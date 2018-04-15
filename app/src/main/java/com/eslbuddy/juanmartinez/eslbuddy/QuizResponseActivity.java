package com.eslbuddy.juanmartinez.eslbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizResponseActivity extends WearableActivity {

    //Constants
    public final static String CORRECT_RESPONSE = "Correct Response";
    public final static String RESPONSE = "Response";
    public final static int REQUEST_CODE = 200;
    public final static int TIMEOUT = 2000;
    public final static String RESULT = "Result";
    public final static int ANSWER_CORRECT = 1;
    public final static int ANSWER_INCORRECT = 0;


    //Handler
    private Handler handler = new Handler();

    //Views
    private TextView resultText;
    private ImageView imageResponse;

    //Results
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_response);

        resultText = findViewById(R.id.trialResult);
        imageResponse = findViewById(R.id.imageRespose);

        final String response = getIntent().getStringExtra(RESPONSE);
        String correctAnswer = getIntent().getStringExtra(CORRECT_RESPONSE);

        if(response.equalsIgnoreCase(correctAnswer)){
            resultText.setText("Good Job!!");
            imageResponse.setImageResource(R.drawable.ok_button);
            result = ANSWER_CORRECT;
        }
        else{
            resultText.setText("Oops, the answer was " + correctAnswer);
            imageResponse.setImageResource(R.drawable.cancel_button);
            result = ANSWER_INCORRECT;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT, result);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }, TIMEOUT);

    }
}
