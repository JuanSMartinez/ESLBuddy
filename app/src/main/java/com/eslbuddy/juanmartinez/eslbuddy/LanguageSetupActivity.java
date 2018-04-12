package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;


public class LanguageSetupActivity extends WearableActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setup);

        TextView inputTextView = findViewById(R.id.inputLanguageText);
        inputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LanguageSelectionActivity.class);
                startActivity(intent);
            }
        });

        TextView outputTextView = findViewById(R.id.outputLanguageText);
        outputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
