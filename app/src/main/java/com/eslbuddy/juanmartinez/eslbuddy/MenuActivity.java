package com.eslbuddy.juanmartinez.eslbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.wear.widget.BoxInsetLayout;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import backend.CRUDHelper;
import backend.Recording;

public class MenuActivity extends WearableActivity {

    //coordinates to detect swipe
    private float x1,y1,x2,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Set button listeners
        setReviewButtonListener();
        setQuizButtonListener();
        setQuizToBuddyButtonListener();
        setSettingsListener();

        setTouchEventListener();
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

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchEventListener() {
        BoxInsetLayout layout = findViewById(R.id.menuActivity);
        layout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getActionMasked();

                switch (action){

                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();

                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        float deltaX = x1-x2;
                        float deltaY = y2-y1;

                        if(deltaX < MainActivity.SWIPE_THRESHOLD && deltaY > MainActivity.SWIPE_THRESHOLD){

                            finish();
                        }
                        return true;
                    default:
                        return true;
                }


            }
        });
    }
}
