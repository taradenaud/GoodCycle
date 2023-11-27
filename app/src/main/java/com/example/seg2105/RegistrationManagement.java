package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegistrationManagement extends AppCompatActivity {

    private List<String> types = new ArrayList<String>();

    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_management);

        Spinner event = (Spinner) findViewById(R.id.EventsSpinner);
        ListView users = (ListView) findViewById(R.id.UserList);
        Button Accept = (Button) findViewById(R.id.AcceptBtn);
        Button Deny = (Button) findViewById(R.id.AcceptBtn);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}