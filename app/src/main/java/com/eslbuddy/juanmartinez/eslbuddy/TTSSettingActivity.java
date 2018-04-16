package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Switch;

import backend.TTSSpeaker;

public class TTSSettingActivity extends WearableActivity {

    //Switch
    private Switch ttsSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttssetting);

        ttsSwitch = findViewById(R.id.ttsSwitch);
        ttsSwitch.setChecked(TTSSpeaker.getInstance(getApplicationContext()).isOn());

        ttsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ttsSwitch.isChecked())
                    TTSSpeaker.getInstance(getApplicationContext()).turnOn();
                else
                    TTSSpeaker.getInstance(getApplicationContext()).turnOff();
            }
        });
        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ttsSwitch.setChecked(TTSSpeaker.getInstance(getApplicationContext()).isOn());
    }


}
