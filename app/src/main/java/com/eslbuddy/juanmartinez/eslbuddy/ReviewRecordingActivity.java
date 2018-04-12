package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

import backend.YandexAPIManager;

public class ReviewRecordingActivity extends WearableActivity implements TextToSpeech.OnInitListener {

    //Constants to identify strings
    public final static String RECORDED_TEXT = "Recorded";
    public final static String TRANSLATION = "Translation";

    //Speak identifier
    private final static String UID = "uid";

    //Text view
    private TextView displayTextView;

    //recorded text
    private String recordedText;

    //translation
    private String translation;

    //Text to speech object
    private TextToSpeech tts;

    //Variable to know if the tts engine was initialized
    private boolean ttsReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_recording);

        ttsReady = false;
        displayTextView =  findViewById(R.id.displayText);
        recordedText = getIntent().getStringExtra(RECORDED_TEXT);
        translation = getIntent().getStringExtra(TRANSLATION);

        displayTextView.setText(recordedText);
        tts = new TextToSpeech(getApplicationContext(), this);
        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onInit(int status) {

        if(status == TextToSpeech.SUCCESS){
            //Play the recorded text
            ttsReady = true;
            speakTranslation();
        }
        else{
            Log.d("Debug:", "Error in status for TTS");
        }
    }

    private void speakRecording(){
        if (ttsReady){
            Locale closest = Locale.forLanguageTag(YandexAPIManager.getInstance(getApplicationContext()).getOriginCode());
            tts.setLanguage(closest);
            tts.speak(recordedText, TextToSpeech.QUEUE_FLUSH, null, UID);
        }
    }

    private void speakTranslation(){
        if(ttsReady){
            Locale closest = Locale.forLanguageTag(YandexAPIManager.getInstance(getApplicationContext()).getTranslationCode());
            tts.setLanguage(closest);
            tts.speak(translation, TextToSpeech.QUEUE_FLUSH, null, UID);
        }
    }
}
