package com.eslbuddy.juanmartinez.eslbuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wear.widget.BoxInsetLayout;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends WearableActivity{

    //Recording permission code
    public final static int SPEECH_RECORDING_PERMISSION = 5000;

    //Swipe threshold
    public final static int SWIPE_THRESHOLD = 100;

    //coordinates to detect swipe
    private float x1,y1,x2,y2;

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

        setTouchEventListener();

        // Enables Always-on
        setAmbientEnabled();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchEventListener() {
        BoxInsetLayout layout = findViewById(R.id.mainLayout);
        layout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getActionMasked();

                switch (action){

                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        return v.performClick();
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        float deltaX = x1-x2;
                        float deltaY = y1-y2;

                        if(deltaX >= SWIPE_THRESHOLD && deltaY < SWIPE_THRESHOLD){
                            Toast.makeText(getApplicationContext(),"Swiped left",Toast.LENGTH_SHORT).show();
                        }
                        else if(deltaX < SWIPE_THRESHOLD && deltaY > SWIPE_THRESHOLD){
                            Toast.makeText(getApplicationContext(),"Swiped up",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        return true;
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




    private boolean checkRecordingPermission(){
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestRecordingPermission(){
        while(!checkRecordingPermission()){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO},
                    SPEECH_RECORDING_PERMISSION);
        }
    }


}
