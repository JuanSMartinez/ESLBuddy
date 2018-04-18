package com.eslbuddy.juanmartinez.eslbuddy;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import backend.CRUDHelper;

public class DeleteDataActivity extends WearableActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        ImageButton okButton = findViewById(R.id.okDelete);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDHelper.deleteAllRecordings(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Deleted all data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ImageButton cancelButton = findViewById(R.id.cancelDelete);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
