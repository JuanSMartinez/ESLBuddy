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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Set button listeners
        setReviewButtonListener();
        setQuizButtonListener();
        setQuizToBuddyButtonListener();
        setSettingsListener();


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

        ImageButton button = findViewById(R.id.quizButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuWordsActivity.class);
                intent.putExtra(MenuWordsActivity.TYPE, MenuWordsActivity.QUIZ);

                startActivity(intent);
            }
        });
    }

    private void setReviewButtonListener() {
        ImageButton button = findViewById(R.id.reviewButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuWordsActivity.class);
                intent.putExtra(MenuWordsActivity.TYPE, MenuWordsActivity.REVIEW);

                startActivity(intent);
            }
        });

    }
}
