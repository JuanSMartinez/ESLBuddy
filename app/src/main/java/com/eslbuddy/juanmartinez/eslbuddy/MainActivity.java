package com.eslbuddy.juanmartinez.eslbuddy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wear.widget.BoxInsetLayout;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import Interfaces.HttpGetListener;
import backend.CRUDHelper;
import backend.HttpQuizManager;
import backend.P2PReceiver;
import backend.Recording;
import backend.TTSSpeaker;
import backend.UserProfile;

public class MainActivity extends WearableActivity implements HttpGetListener{

    //Recording permission code
    public final static int SPEECH_RECORDING_PERMISSION = 5000;
    public final static int INTERNET_PERMISSION = 12;

    //Server error codes
    public final static String FETCHED = "FETCHED";
    public final static String NO_QUIZ = "NO_QUIZ";

    //Swipe threshold
    public final static int SWIPE_THRESHOLD = 100;

    //seconds to wait between subsequent GET requests
    public final static int TIMEOUT = 5000;

    //Handler
    private Handler handler = new Handler();


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
                intent.putExtra(RecordingActivity.RECORDING_TYPE, RecordingActivity.RECORDING_LOCAL);
                startActivity(intent);


            }
        });

        ImageButton arrowRightButton = findViewById(R.id.arrowRightButton);
        arrowRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ArrayList<Recording> recent = CRUDHelper.getRecentRecordings(getApplicationContext());

            Intent intent = new Intent(getApplicationContext(), ListOfWordsActivity.class);
            intent.putExtra(ListOfWordsActivity.LIST, recent);
            startActivity(intent);

            }
        });

        ImageButton arrowDownButton = findViewById(R.id.arrowDownButton);
        arrowDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);

            }
        });

        setTouchEventListener();

        //Initialize tts
        TTSSpeaker.getInstance(getApplicationContext());

        //Set runner
        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                fetchQuiz();
                handler.postDelayed(this, TIMEOUT);
            }
        }, TIMEOUT);

        // Enables Always-on
        setAmbientEnabled();

    }

    private void fetchQuiz() {
        HttpQuizManager manager = HttpQuizManager.getInstance(getApplicationContext());
        manager.getAQuiz(UserProfile.getInstance().getName(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();

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

    private boolean checkSharingPermissions(){
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

        else
            return true;
    }

    private void requestPermissions(){
        while(!checkSharingPermissions())
            ;
    }

    @Override
    public void processStringGetResponse(String response) {
        if(!response.equals(FETCHED) && !response.equals(NO_QUIZ)) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            long[] vibrationPattern = {0, 500, 50, 300};
            //-1 - don't repeat
            final int indexInPatternToRepeat = -1;
            vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
            ArrayList<Recording> singleArray = new ArrayList<>();
            Recording temporal = new Recording("Temporal", response);
            singleArray.add(temporal);
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra(ListOfWordsActivity.LIST, singleArray);
            startActivity(intent);
        }
    }
}
