package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import backend.HttpQuizManager;

public class ShareToUserActivity extends WearableActivity {

    public final static String QUIZ_TO_SHARE = "Quiz to share";
    private EditText shareUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to_user);

        shareUser = findViewById(R.id.editTextShare);
        final String quiz = getIntent().getStringExtra(QUIZ_TO_SHARE);

        ImageButton share = findViewById(R.id.okButtonShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameOther = shareUser.getText().toString();
                HttpQuizManager.getInstance(getApplicationContext()).postQuiz(userNameOther,quiz);
            }
        });

        ImageButton cancel = findViewById(R.id.cancelButtonShare);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
