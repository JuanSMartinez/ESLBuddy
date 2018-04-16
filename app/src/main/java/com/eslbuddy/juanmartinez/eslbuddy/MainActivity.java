package com.eslbuddy.juanmartinez.eslbuddy;

import android.Manifest;
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

import java.util.ArrayList;

import backend.CRUDHelper;
import backend.Recording;
import backend.TTSSpeaker;

public class MainActivity extends WearableActivity{

    //Recording permission code
    public final static int SPEECH_RECORDING_PERMISSION = 5000;
    public final static int INTERNET_PERMISSION = 12;
    public final static int COARSE_LOCATION_PERMISSION = 13;
    public final static int ACCESS_WIFI_STATE = 14;
    public final static int CHANGE_WIFI_STATE = 15;

    //Swipe threshold
    public final static int SWIPE_THRESHOLD = 100;

    //coordinates to detect swipe
    private float x1,y1,x2,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request permission
        requestPermissions();


        ImageButton recordButton = findViewById(R.id.recordButton);
        //Button recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RecordingActivity.class);
                startActivity(intent);


            }
        });

        setTouchEventListener();

        //Initialize tts
        TTSSpeaker.getInstance(getApplicationContext());

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
                    //Message by SangWon
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        //return v.performClick();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        float deltaX = x1-x2;
                        float deltaY = y1-y2;

                        if(deltaX >= SWIPE_THRESHOLD && deltaY < SWIPE_THRESHOLD){
                            ArrayList<Recording> recent = CRUDHelper.getRecentRecordings(getApplicationContext());

                            Intent intent = new Intent(getApplicationContext(), ListOfWordsActivity.class);
                            intent.putExtra(ListOfWordsActivity.LIST, recent);
                            startActivity(intent);
                        }
                        else if(deltaX < SWIPE_THRESHOLD && deltaY > SWIPE_THRESHOLD){
                            //Toast.makeText(getApplicationContext(),"Swiped up",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
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
        TTSSpeaker.getInstance(getApplicationContext()).finishTTS();
        super.onDestroy();

    }


    private boolean checkSharingPersimssions(){
        if (!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    SPEECH_RECORDING_PERMISSION);
            return false;
        }
        else if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                    INTERNET_PERMISSION);
            return false;
        }
        else if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    COARSE_LOCATION_PERMISSION);
            return false;
        }
        else if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    ACCESS_WIFI_STATE);
            return false;
        }
        else if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE},
                    CHANGE_WIFI_STATE);
            return false;
        }
        else
            return true;
    }

    private void requestPermissions(){
        while(!checkSharingPersimssions())
            ;
    }


}
