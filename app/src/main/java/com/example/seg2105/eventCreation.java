package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class eventCreation extends AppCompatActivity {

    private ListView TypesList;
    private ArrayList<String> ArrayList = new ArrayList<String>();
    private ArrayAdapter adapter;
    private DatabaseReference ref;
    private String Selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_creation);

        Button backButton = findViewById(R.id.backbutton);
        Spinner eventType = (Spinner) findViewById(R.id.eventType);
        eventType.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference("eventtypes");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String type = snapshot.child("name").getValue(String.class);
                ArrayList.add(type);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(ArrayList.size() > 0){
                    ArrayList.clear();
                }
                String type = snapshot.child("eventtype").getValue(String.class);
                ArrayList.add(type);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
        int size = ArrayList.size();
        String[] types = new String[size];
        for (int i= 0; i<size; i++){
            types[i] = ArrayList.get(i);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item , types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Button CreateEvent = (Button) findViewById(R.id.createEventBtn);
        EditText eventName = (EditText) findViewById(R.id.eventName);
        EditText location = (EditText) findViewById(R.id.region);
        EditText DifficultyLevel = (EditText) findViewById(R.id.level);
        EditText pace = (EditText) findViewById(R.id.pace);
        EditText regFee = (EditText) findViewById(R.id.RegFee);
        EditText participantLimit = (EditText) findViewById(R.id.participantlimit);
        CreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for (int i = 0; i< ArrayList.size(); i++){
                    if( eventType.getSelectedItem().equals(ArrayList.get(i))){
                        count = i;
                    }
                }
                String eT = String.valueOf(ArrayList.get(count));
                String eN = String.valueOf(eventName.getText());
                String loc = String.valueOf(location.getText());
                String DL = String.valueOf(DifficultyLevel.getText());
                String p = String.valueOf(pace.getText());
                String rF = String.valueOf(regFee.getText());
                String Pl = String.valueOf(participantLimit.getText());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference eventName = database.getReference("events/"+eN+"/name");
                DatabaseReference eventType = database.getReference("events/"+eN+"/eventtype");
                DatabaseReference Location = database.getReference("events/"+eN+"/location");
                DatabaseReference Level = database.getReference("events/"+eN+"/level");
                DatabaseReference pace = database.getReference("events/"+eN+"/pace");
                DatabaseReference regFee = database.getReference("events/"+eN+"/registrationFee");
                DatabaseReference participantLimit = database.getReference("events/"+eN+"/participantLimit");

                eventName.setValue(eN);
                eventType.setValue(eT);
                Location.setValue(loc);
                Level.setValue(DL);
                pace.setValue(p);
                regFee.setValue(rF);
                participantLimit.setValue(Pl);

                finish();
            }
        });
    }
}