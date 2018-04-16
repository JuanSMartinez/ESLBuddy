package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import backend.TTSSpeaker;
import backend.YandexAPIManager;

public class ReviewRecordingActivity extends WearableActivity {

    //Constants to identify strings
    public final static String RECORDED_TEXT = "Recorded";
    public final static String TRANSLATION = "Translation";

    //Permssions codes

    //Speak identifier
    private final static String UID = "uid";

    //Text view
    private TextView displayTextView;

    //Switch between words
    private Switch switchWords;

    //recorded text
    private String recordedText;

    //translation
    private String translation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_recording);

        displayTextView =  findViewById(R.id.displayText);
        recordedText = getIntent().getStringExtra(RECORDED_TEXT);
        translation = getIntent().getStringExtra(TRANSLATION);

        displayTextView.setText(recordedText);
        switchWords = findViewById(R.id.switchTextReviewed);

        switchWords.setChecked(false);
        switchWords.setText("Original Word");
        switchWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchWords.isChecked()) {
                    displayTextView.setText(translation);
                    switchWords.setText("Translation");

                }
                else {
                    speakRecording();
                    displayTextView.setText(recordedText);
                    switchWords.setText("Original Word");
                }
            }
        });
        // Enables Always-on
        setAmbientEnabled();

        speakRecording();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    private void speakRecording(){
        TTSSpeaker.getInstance(getApplicationContext()).speakText(recordedText);
    }


}
