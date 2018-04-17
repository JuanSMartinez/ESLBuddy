package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import Interfaces.CircularViewClickListener;
import Interfaces.CustomListAdapter;
import Interfaces.CustomScrollingLayoutCallback;
import backend.CRUDHelper;

public class SettingsActivity extends WearableActivity implements CircularViewClickListener{


    //Recycler view for circular layout
    private WearableRecyclerView mWearableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mWearableRecyclerView = findViewById(R.id.settingsActivityLayout);
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        String[] data = {"Language", "TTS", "Delete", "Name"};
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
        if(textInView.equals("Language")){
            Intent intent = new Intent(getApplicationContext(), LanguageSetupActivity.class);
            startActivity(intent);
        }
        else if(textInView.equals("TTS")){
            Intent intent = new Intent(getApplicationContext(), TTSSettingActivity.class);
            startActivity(intent);
        }
        else if(textInView.equals("Name")){
            Intent intent = new Intent(getApplicationContext(), NameSettingsActivity.class);
            startActivity(intent);
        }
        else if(textInView.equals("Delete")){
            CRUDHelper.deleteAllRecordings(getApplicationContext());
            Toast.makeText(getApplicationContext(), "Deleted all data", Toast.LENGTH_SHORT).show();
        }
    }
}
