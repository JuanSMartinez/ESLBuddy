package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import Interfaces.CustomListAdapter;
import Interfaces.CustomScrollingLayoutCallback;

public class RecentWordsActivity extends WearableActivity {

    private WearableRecyclerView mWearableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_words);

        mWearableRecyclerView =  findViewById(R.id.recentWordsList);
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        String[] data = new String[100];
        for(int i = 0; i < 100; i ++){
            data[i] = "Data " + i;
        }
        CustomListAdapter adapter = new CustomListAdapter(data);
        mWearableRecyclerView.setAdapter(adapter);

        mWearableRecyclerView.setCircularScrollingGestureEnabled(true);
        mWearableRecyclerView.setCircularScrollingGestureEnabled(true);
        mWearableRecyclerView.setBezelFraction(0.5f);
        mWearableRecyclerView.setScrollDegreesPerScreen(90);

        CustomScrollingLayoutCallback customScrollingLayoutCallback = new CustomScrollingLayoutCallback();
        mWearableRecyclerView.setLayoutManager(new WearableLinearLayoutManager(this, customScrollingLayoutCallback));


        // Enables Always-on
        setAmbientEnabled();
    }
}
