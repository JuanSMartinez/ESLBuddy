package com.eslbuddy.juanmartinez.eslbuddy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;
import java.util.Set;

import backend.TTSManager;
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

    //Text to speech object
    private TTSSpeaker ttsSpeakerOrigin;

    private TTSSpeaker ttsSpeakerTranslation;


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
                    speakTranslation();
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

        ttsSpeakerOrigin = new TTSSpeaker(this, YandexAPIManager.getInstance(getApplicationContext()).getOriginCode(), "Origin");
        ttsSpeakerTranslation = new TTSSpeaker(this, YandexAPIManager.getInstance(getApplicationContext()).getTranslationCode(), "Translation");

    }

    @Override
    protected void onDestroy() {
        if(ttsSpeakerOrigin != null){
            ttsSpeakerOrigin.finishTTS();
        }
        if(ttsSpeakerTranslation != null){
            ttsSpeakerTranslation.finishTTS();
        }
        super.onDestroy();
    }


    private void speakRecording(){
        if (ttsSpeakerOrigin.isInitialized() && TTSManager.getInstance().isOn()){

            ttsSpeakerOrigin.speakText(recordedText);
        }
        else
            Log.d("Debug", "Not Initialized");
    }

    private void speakTranslation(){
        if (ttsSpeakerTranslation.isInitialized() && TTSManager.getInstance().isOn()){

            ttsSpeakerTranslation.speakText(translation);
        }
        else
            Log.d("Debug", "Not Initialized");
    }


}
