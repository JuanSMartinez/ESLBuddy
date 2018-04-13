package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

import backend.YandexAPIManager;


public class LanguageSetupActivity extends WearableActivity {


    //type of selection
    public final static String TYPE = "Type";

    public final static int INPUT = 0;

    public final static int OUTPUT = 1;

    //Views
    private TextView inputTextView;
    private TextView outputTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setup);

        //This should not be subject to change, always English
        inputTextView = findViewById(R.id.inputLanguageText);
        inputTextView.setText(YandexAPIManager.getInstance(getApplicationContext()).getOriginLanguage());
        /*inputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LanguageSelectionActivity.class);
                intent.putExtra(TYPE, INPUT);
                startActivity(intent);
            }
        });*/

        outputTextView = findViewById(R.id.outputLanguageText);
        outputTextView.setText(YandexAPIManager.getInstance(getApplicationContext()).getTranslationLanguage());
        outputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LanguageSelectionActivity.class);
                intent.putExtra(TYPE, OUTPUT);
                startActivity(intent);
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputTextView.setText(YandexAPIManager.getInstance(getApplicationContext()).getOriginLanguage());
        outputTextView.setText(YandexAPIManager.getInstance(getApplicationContext()).getTranslationLanguage());
    }
}
