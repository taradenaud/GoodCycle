package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

public class Admin_events extends AppCompatActivity {

    private ListView eventslist;
    private List<eventlistitem> ArrayList = new ArrayList<>();
    private eventadapter adapter;
    private DatabaseReference ref;
    private String Selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events);

        ref = FirebaseDatabase.getInstance().getReference("events");
        adapter = new eventadapter(this, R.layout.event_list_item, ArrayList);
        eventslist = findViewById(R.id.EventView);
        eventslist.setAdapter(adapter);

        EditText selectedEvent = findViewById(R.id.SelectedEvent); //Selection identifier at top of page
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Called when loading list
                String name = snapshot.child("name").getValue(String.class);
                String type = snapshot.child("eventtype").getValue(String.class);
                String level = snapshot.child("level").getValue(String.class);
                String location = snapshot.child("location").getValue(String.class);
                String pace = snapshot.child("pace").getValue(String.class);
                String limit = snapshot.child("participantLimit").getValue(String.class);
                String fee = snapshot.child("registrationFee").getValue(String.class);

                ArrayList.add(new eventlistitem(name, type, location, level, pace, limit, fee));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Refresh list as something was changed
                if(ArrayList.size() > 0){
                    ArrayList.clear();
                }
                String name = snapshot.child("name").getValue(String.class);
                String type = snapshot.child("eventtype").getValue(String.class);
                String level = snapshot.child("level").getValue(String.class);
                String location = snapshot.child("location").getValue(String.class);
                String pace = snapshot.child("pace").getValue(String.class);
                String limit = snapshot.child("participantLimit").getValue(String.class);
                String fee = snapshot.child("registrationFee").getValue(String.class);
                ArrayList.add(new eventlistitem(name, type, location, level, pace, limit, fee));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { //Something is Deleted from Database, all that needs to happen is remove deleted item from arraylist of users
                for (int i = 0; i < ArrayList.size(); i++){ //Not efficient needs optimization
                    if(ArrayList.get(i).getName().equals(snapshot.child("name").getValue(String.class))){
                        ArrayList.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        eventslist.setItemsCanFocus(true);
        eventslist.setOnItemClickListener(new AdapterView.OnItemClickListener() { //set selected user based on user list click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventlistitem name = (eventlistitem) parent.getItemAtPosition(position);
                Selected = name.getName();
                selectedEvent.setText(Selected);
            }
        });

        Button changeDetail = (Button) findViewById(R.id.editvalueBtn);
        Spinner propertySelect = (Spinner) findViewById(R.id.propertyselect);

        String[] entries = getResources().getStringArray(R.array.event_properties_entries);
        String[] values = getResources().getStringArray(R.array.event_properties_values);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, entries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySelect.setAdapter(adapter);
        EditText NewDetailValue = (EditText) findViewById(R.id.detailValue);

        changeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ValueToChange;
                int count = 0;
                for (int i = 0; i<entries.length; i++){
                    if( propertySelect.getSelectedItem().equals(entries[i])){
                        count = i;
                    }
                }
                ValueToChange = values[count];
                DatabaseReference changedDetailRef = ref.getDatabase().getReference("events/" + Selected + "/" + ValueToChange);
                changedDetailRef.setValue(String.valueOf(NewDetailValue.getText()));
            }
        });

        Button deleteEvent = (Button) findViewById(R.id.deleteEventBtn);
        deleteEvent.setOnClickListener(new View.OnClickListener() { //delete button functionality
            @Override
            public void onClick(View v) {
                ref.child(Selected).removeValue();
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