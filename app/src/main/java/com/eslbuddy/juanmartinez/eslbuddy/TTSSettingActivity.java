package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import backend.TTSManager;

public class TTSSettingActivity extends WearableActivity {

    //Switch
    private Switch ttsSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttssetting);

        ttsSwitch = findViewById(R.id.ttsSwitch);
        ttsSwitch.setChecked(TTSManager.getInstance().isOn());

        ttsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ttsSwitch.isChecked())
                    TTSManager.getInstance().turnOn();
                else
                    TTSManager.getInstance().turnOff();
            }
        });
        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ttsSwitch.setChecked(TTSManager.getInstance().isOn());
    }


}
