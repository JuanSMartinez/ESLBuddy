package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import backend.UserProfile;

public class NameSettingsActivity extends WearableActivity {

    //Edit Text
    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_settings);

        nameText = findViewById(R.id.editTextName);
        nameText.setText(UserProfile.getInstance().getName());

        ImageButton okButton = findViewById(R.id.okButtonName);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile.getInstance().setName(nameText.getText().toString());
                finish();
            }
        });

        ImageButton cancelButton = findViewById(R.id.cancelButtonName);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
