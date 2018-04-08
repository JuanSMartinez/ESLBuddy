package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Interfaces.CustomListAdapter;
import Interfaces.CustomScrollingLayoutCallback;
import backend.CRUDHelper;
import backend.Recording;

public class RecentWordsActivity extends WearableActivity {

    //Number of recent words to display
    public final static int NUMBER_RECENT_WORDS = 30;

    //Recycler view for circular layout
    private WearableRecyclerView mWearableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_words);

        mWearableRecyclerView =  findViewById(R.id.recentWordsList);
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        ArrayList<Recording> recordings = CRUDHelper.getRecentRecordings(getApplicationContext());
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
        CustomListAdapter adapter = new CustomListAdapter(data);
        mWearableRecyclerView.setAdapter(adapter);

        mWearableRecyclerView.setCircularScrollingGestureEnabled(true);
        mWearableRecyclerView.setBezelFraction(0.5f);
        mWearableRecyclerView.setScrollDegreesPerScreen(90);

        // Enables Always-on
        setAmbientEnabled();

        CustomScrollingLayoutCallback customScrollingLayoutCallback = new CustomScrollingLayoutCallback();
        mWearableRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this, customScrollingLayoutCallback));
    }
}
