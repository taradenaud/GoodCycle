package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class clubOwner_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_owner_dashboard);

        Button addEvent = (Button) findViewById(R.id.buttonEventCreation);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( clubOwner_dashboard.this, eventCreation.class));
            }
        });
    }
}