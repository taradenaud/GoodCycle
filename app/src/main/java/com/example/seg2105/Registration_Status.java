package com.example.seg2105;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Registration_Status extends AppCompatActivity {

    private ListView eventsListView;
    private List<EventWithStatus> eventList = new ArrayList<>();
    private EventWithStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_status);

        eventsListView = findViewById(R.id.regStatus);
        adapter = new EventWithStatusAdapter(this, R.layout.reg_status_list_item, eventList);
        eventsListView.setAdapter(adapter);

        fetchRegistrationStatus();

        Button back = findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Navigate back to the previous page
            }
        });
    }

    private void fetchRegistrationStatus() {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(LoginPage.Username) // Replace with the actual user identifier
                .child("events");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String eventName = eventSnapshot.getKey();
                    String status = eventSnapshot.getValue(String.class);
                    // Since other event details are not available here, pass null or placeholders
                    eventList.add(new EventWithStatus(eventName, status));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}

