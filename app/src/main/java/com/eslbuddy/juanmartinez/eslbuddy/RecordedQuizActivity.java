package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import backend.CRUDHelper;
import backend.YandexAPIManager;

public class RecordedQuizActivity extends WearableActivity {

    private TextView recordedTextView;
    private TextView translatedTextView;

    private String recordedText;
    private String translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_quiz);

        recordedText = getIntent().getStringExtra(YandexAPIManager.RECORDED_TEXT);
        translatedText = getIntent().getStringExtra(YandexAPIManager.TRANSLATED_TEXT);

        recordedTextView = findViewById(R.id.recordedTextViewQuiz);
        translatedTextView = findViewById(R.id.translatedTextViewQuiz);

        recordedTextView.setText(recordedText);
        translatedTextView.setText(translatedText);

        setButtonListeners();

        // Enables Always-on
        setAmbientEnabled();
    }

    private void setButtonListeners() {

        ImageButton shareButton = findViewById(R.id.shareButtonQuiz);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quiz = recordedText + "&" + translatedText;
                Intent intent = new Intent(getApplicationContext(), ShareToUserActivity.class);
                intent.putExtra(ShareToUserActivity.QUIZ_TO_SHARE, quiz);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            finish();

        }
        return super.onKeyDown(keyCode, event);
    }


}
