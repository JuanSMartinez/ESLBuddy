package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import Interfaces.CircularViewClickListener;
import Interfaces.CustomListAdapter;
import Interfaces.CustomScrollingLayoutCallback;
import backend.CRUDHelper;
import backend.Recording;

public class MenuWordsActivity extends WearableActivity implements CircularViewClickListener{

    //Identifiers for the types of lists
    public final static String RECENT = "Recent";
    public final static String WRONG = "Wrong";
    public final static String RANDOM = "Random";
    public final static String ALL = "All";

    //Identifiers to the type of activity to be launched when selecting a list
    public final static String TYPE = "Type";
    public final static int REVIEW = 0;
    public final static int QUIZ = 1;

    //Recycler view for circular layout
    private WearableRecyclerView mWearableRecyclerView;

    //Lists of words

    //All recordings
    private ArrayList<Recording> allRecordings;

    //Recent recordings
    private ArrayList<Recording> recentRecordings;

    //Recordings marked as wrong
    private ArrayList<Recording> wrongRecordings;

    //Random recordings
    private ArrayList<Recording> randomRecordings;

    //Type of activity to launch
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_words);


        mWearableRecyclerView =  findViewById(R.id.menuWordsLayout);
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        //Retrieve lists
        allRecordings = (ArrayList<Recording>) getIntent().getSerializableExtra(ALL);
        wrongRecordings = (ArrayList<Recording>) getIntent().getSerializableExtra(WRONG);
        randomRecordings = (ArrayList<Recording>) getIntent().getSerializableExtra(RANDOM);
        recentRecordings = (ArrayList<Recording>) getIntent().getSerializableExtra(RECENT);
        type = getIntent().getIntExtra(TYPE, REVIEW);


        ArrayList<Recording> recordings = CRUDHelper.getRecentRecordings(getApplicationContext());
        String[] data = new String[]{"Recent (" + recordings.size() +")",
                "Wrong Answer (" + wrongRecordings.size() +")",
                "Random (" + randomRecordings.size() + ")",
                "All (" + allRecordings.size() + ")"};

        CustomListAdapter adapter = new CustomListAdapter(data, this);
        mWearableRecyclerView.setAdapter(adapter);

        mWearableRecyclerView.setCircularScrollingGestureEnabled(true);
        mWearableRecyclerView.setBezelFraction(0.5f);
        mWearableRecyclerView.setScrollDegreesPerScreen(90);

        // Enables Always-on
        setAmbientEnabled();

        CustomScrollingLayoutCallback customScrollingLayoutCallback = new CustomScrollingLayoutCallback();
        mWearableRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this, customScrollingLayoutCallback));

    }

    @Override
    public void onClickListItem(String textInView) {
        ArrayList<Recording> picked = new ArrayList<>();
        if(textInView.startsWith("Recent"))
            picked = recentRecordings;
        else if(textInView.startsWith("Wrong"))
            picked = wrongRecordings;
        else if(textInView.startsWith("Random"))
            picked = randomRecordings;
        else if(textInView.startsWith("All"))
            picked = allRecordings;

        Intent intent;
        if(type == REVIEW)
            intent = new Intent(this, ListOfWordsActivity.class);
        else
            intent = new Intent(this, QuizActivity.class);
        intent.putExtra(ListOfWordsActivity.LIST, picked);
        startActivity(intent);
    }
}
