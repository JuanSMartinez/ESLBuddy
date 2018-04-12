package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import Interfaces.CircularViewClickListener;
import Interfaces.CustomListAdapter;
import Interfaces.CustomScrollingLayoutCallback;
import backend.YandexAPIManager;

public class LanguageSelectionActivity extends WearableActivity implements CircularViewClickListener{

    private WearableRecyclerView mWearableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);


        mWearableRecyclerView =  findViewById(R.id.languageSelectionView);
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        ArrayList<String> languages = YandexAPIManager.getInstance(getApplicationContext()).getLanguages();

        CustomListAdapter adapter = new CustomListAdapter(languages.toArray(new String[0]), this);
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

        int type = getIntent().getIntExtra(LanguageSetupActivity.TYPE, 0);
        if (type == LanguageSetupActivity.INPUT)
            YandexAPIManager.getInstance(getApplicationContext()).setOriginLanguage(textInView);
        else if(type == LanguageSetupActivity.OUTPUT)
            YandexAPIManager.getInstance(getApplicationContext()).setTranslationLanguage(textInView);

        finish();
    }
}
