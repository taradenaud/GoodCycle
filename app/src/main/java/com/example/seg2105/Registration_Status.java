package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration_Status extends AppCompatActivity {

    private TextView regStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_status);

        regStatus = (TextView) findViewById(R.id.regStatus);
        fetchRegistrationStatus();

        Button back = (Button) findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
    }

    private void fetchRegistrationStatus() {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(LoginPage.Username)
                .child("events");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder statusBuilder = new StringBuilder();

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String eventName = eventSnapshot.getKey();
                    String status = eventSnapshot.getValue(String.class);
                    statusBuilder.append(eventName).append(": ").append(status).append("\n");
                }

                regStatus.setText(statusBuilder.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
