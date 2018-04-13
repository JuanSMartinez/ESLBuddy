package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;

import java.util.ArrayList;

import Interfaces.CircularViewClickListener;
import Interfaces.CustomListAdapter;
import Interfaces.CustomScrollingLayoutCallback;
import backend.CRUDHelper;
import backend.Recording;

public class ListOfWordsActivity extends WearableActivity implements CircularViewClickListener {

    //Identifier for the list
    public final static String LIST = "List";

    //Recycler view for circular layout
    private WearableRecyclerView mWearableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);

        mWearableRecyclerView =  findViewById(R.id.wordsList);
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        ArrayList<Recording> recordings = (ArrayList<Recording>) getIntent().getSerializableExtra(LIST);
        String[] data;
        if(recordings.size() == 0){
            data = new String[1];
            data[0] = "No Recordings";
        }
        else {
            data = new String[recordings.size()];
            for (int i = 0; i < recordings.size(); i++) {
                Recording recording = recordings.get(i);
                data[i] = recording.getRecordedText().split(":")[0];
            }
        }
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
        Intent intent = new Intent(getApplicationContext(), ReviewRecordingActivity.class);
        Recording recording = CRUDHelper.getRecordingFromOriginText(getApplicationContext(), textInView);
        if(recording != null){
            String[] data = recording.getRecordedText().split(":");
            intent.putExtra(ReviewRecordingActivity.RECORDED_TEXT, data[0]);
            intent.putExtra(ReviewRecordingActivity.TRANSLATION, data[1]);
            startActivity(intent);
        }
    }
}
