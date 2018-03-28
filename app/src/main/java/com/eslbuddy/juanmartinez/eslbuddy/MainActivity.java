package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends WearableActivity{

    //Speech recognition request code
    public final static int SPEECH_RECOGNIZTION_REQUEST_CODE = 8080;

    //Recording permission code
    public final static int SPEECH_RECORDING_PERMISSION = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request permission
        requestRecordingPermission();


        ImageButton recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordingActivity.class);
                startActivity(intent);
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private boolean checkRecordingPermission(){
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestRecordingPermission(){
        while(!checkRecordingPermission()){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO},
                    SPEECH_RECORDING_PERMISSION);
        }
    }

}
