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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class eventCreation extends AppCompatActivity {


    private List<String> types = new ArrayList<String>();
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_creation);

        Button backButton = findViewById(R.id.backbutton);
        Spinner eventType = (Spinner) findViewById(R.id.eventType);
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("eventtypes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot eventtypes : snapshot.getChildren()){
                    String eventtype = eventtypes.child("name").getValue(String.class);
                    if (eventtype!=null){
                        types.add(eventtype);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(eventCreation.this, android.R.layout.simple_spinner_item , types);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                eventType.setAdapter(adapter);
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

                String eT = types.get(eventType.getSelectedItemPosition()).toString();
                String eN = String.valueOf(eventName.getText());
                String loc = String.valueOf(location.getText());
                String DL = String.valueOf(DifficultyLevel.getText());
                String p = String.valueOf(pace.getText());
                String rF = String.valueOf(regFee.getText());
                Integer Pl = Integer.valueOf(String.valueOf(participantLimit.getText()));

                CreateEvent(LoginPage.Username, eT,eN, loc,DL, p, rF, Pl);

                finish();
            }
        });
    }
    static protected void CreateEvent(String clubname, String eT, String eN, String loc, String DL, String pace, String rF, Integer Pl){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventName = database.getReference("clubs/"+clubname+"/events/"+eN+"/name");
        DatabaseReference eventType = database.getReference("clubs/"+clubname+"/events/"+eN+"/eventtype");
        DatabaseReference Location = database.getReference("clubs/"+clubname+"/events/"+eN+"/location");
        DatabaseReference Level = database.getReference("clubs/"+clubname+"/events/"+eN+"/level");
        DatabaseReference Pace = database.getReference("clubs/"+clubname+"/events/"+eN+"/pace");
        DatabaseReference regFee = database.getReference("clubs/"+clubname+"/events/"+eN+"/registrationFee");
        DatabaseReference participantLimit = database.getReference("clubs/"+clubname+"/events/"+eN+"/participantLimit");

        eventName.setValue(eN);
        eventType.setValue(eT);
        Location.setValue(loc);
        Level.setValue(DL);
        Pace.setValue(pace);
        regFee.setValue(rF);
        participantLimit.setValue(Pl);
    }
}