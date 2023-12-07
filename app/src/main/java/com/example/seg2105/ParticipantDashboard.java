package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParticipantDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_dashboard);

        TextView welcome = (TextView) findViewById(R.id.Participant_Welcome);


        Button Discover = (Button) findViewById(R.id.EventDiscBtn);
        Discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ParticipantDashboard.this, Event_Discovery.class));
            }
        });

        Button RegStatus = (Button) findViewById(R.id.RegistrationStatusBtn);
        RegStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ParticipantDashboard.this, Registration_Status.class));
            }
        });

        Button clubRate = (Button) findViewById(R.id.clubRatingDashboardBtn);
        clubRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ParticipantDashboard.this, ClubRatingPage.class));
            }
        });

        Button back = (Button) findViewById(R.id.BackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
    }
}