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
        Button eventEdit = (Button) findViewById(R.id.EventEditButton);
        eventEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( clubOwner_dashboard.this, ManageClubEvents.class));
            }
        });

        Button RegManagement = (Button) findViewById(R.id.buttonRegistrationManagement);
        RegManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( clubOwner_dashboard.this, RegistrationManagement.class));
            }
        });
        Button ResAndAwards = (Button) findViewById(R.id.buttonResultsAndAwards);
        ResAndAwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( clubOwner_dashboard.this, ClubAwardDashboard.class));
            }
        });
        Button ProfileMngmnt = (Button) findViewById(R.id.buttonProfilemngmnt);
        ProfileMngmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( clubOwner_dashboard.this, ClubProfileEditor.class));
            }
        });

        Button back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
    }
}