package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class SavedRecordingActivityPopUp extends WearableActivity {

    public final static int TIMEOUT = 1000;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recording_pop_up);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, TIMEOUT);
    }
}
