package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import backend.CRUDHelper;
import backend.Recording;

public class MenuActivity extends WearableActivity {

    //All recordings
    private ArrayList<Recording> allRecordings;

    //Recent recordings
    private ArrayList<Recording> recentRecordings;

    //Recordings marked as wrong
    private ArrayList<Recording> wrongRecordings;

    //Random recordings
    private ArrayList<Recording> randomRecordings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Set button listeners
        setReviewButtonListener();
        setQuizButtonListener();
        setQuizToBuddyButtonListener();
        setSettingsListener();

        //Get the lists of recordings
        //TODO: Warning, this is highly inefficient in terms of memory, for more data, a database is needed
        allRecordings = CRUDHelper.getRecordings(this);
        recentRecordings = CRUDHelper.getRecentRecordings(this);
        wrongRecordings = CRUDHelper.getAllWrongRecordings(this);
        randomRecordings = CRUDHelper.getRandomRecordings(this);

        // Enables Always-on
        setAmbientEnabled();
    }

    private void setSettingsListener() {
        ImageButton button = findViewById(R.id.settings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setQuizToBuddyButtonListener() {
    }

    private void setQuizButtonListener() {
    }

    private void setReviewButtonListener() {
        ImageButton button = findViewById(R.id.reviewButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuWordsActivity.class);
                intent.putExtra(MenuWordsActivity.TYPE, MenuWordsActivity.REVIEW);
                intent.putExtra(MenuWordsActivity.RECENT, recentRecordings);
                intent.putExtra(MenuWordsActivity.WRONG, wrongRecordings);
                intent.putExtra(MenuWordsActivity.RANDOM, randomRecordings);
                intent.putExtra(MenuWordsActivity.ALL, allRecordings);
                startActivity(intent);
            }
        });

    }
}
