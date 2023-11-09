package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        Button ManageUsrs = (Button) findViewById(R.id.ManageUsrsbtn);

        ManageUsrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( admin_dashboard.this, AdminManageUsers.class));
            }
        });

        Button events = (Button) findViewById(R.id.EventTypesbtn);

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( admin_dashboard.this, AdminEventTypes.class));
            }
        });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });

    }
}