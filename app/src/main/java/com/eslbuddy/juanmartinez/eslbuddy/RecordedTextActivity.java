package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import backend.CRUDHelper;
import backend.YandexAPIManager;

public class RecordedTextActivity extends WearableActivity {



    private TextView recordedTextView;
    private TextView translatedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_text);

        String recordedText = getIntent().getStringExtra(YandexAPIManager.RECORDED_TEXT);
        String translatedText = getIntent().getStringExtra(YandexAPIManager.TRANSLATED_TEXT);

        recordedTextView = findViewById(R.id.recordedTextView);
        translatedTextView = findViewById(R.id.translatedTextView);

        recordedTextView.setText(recordedText);
        translatedTextView.setText(translatedText);

        setButtonListeners();

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            finish();
            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


    private void setButtonListeners(){
        ImageButton saveTranslationButton = findViewById(R.id.okButton);
        ImageButton cancelButton = findViewById(R.id.cancelButton);

        saveTranslationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recordedText = recordedTextView.getText().toString();
                String translatedText = translatedTextView.getText().toString();
                CRUDHelper.createRecording(recordedText + "&" + translatedText, getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), SavedRecordingActivityPopUp.class);
                startActivity(intent);
                finish();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
            }
        });
    }
}
