package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event_Discovery extends AppCompatActivity {

    private ListView eventslist;
    private List<String> SearchSpinnerList = new ArrayList<>();
    private List<eventlistitem> ArrayList;
    private ArrayAdapter<String> searchAdapter;
    private eventadapter adapter;
    private DatabaseReference ref;
    private Map<String, String> clubsDict;
    private String Club;
    private String Selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_discovery);

        //search Text box
        EditText searchParamText =  findViewById(R.id.detailValue);

        //Search Parameter spinner and visibility toggle for text box
        Spinner propertySelect =  findViewById(R.id.propertyselect);
        Spinner search_option_spinner = findViewById(R.id.searchspinner);

        String[] entries = getResources().getStringArray(R.array.search_selection);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, entries);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySelect.setAdapter(spinnerAdapter);

        //DB List Population
        eventslist = findViewById(R.id.EventView);
        ArrayList = new ArrayList<>();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        adapter = new eventadapter(Event_Discovery.this, R.layout.event_list_item, ArrayList);
        eventslist.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference("clubs");

        //WORKS

        propertySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("All")){
                    searchParamText.setVisibility(View.GONE);
                    search_option_spinner.setVisibility(View.GONE);
                }
                else if(selectedItem.equals("Event Name")){
                    searchParamText.setVisibility(View.VISIBLE);
                    search_option_spinner.setVisibility(View.GONE);
                }
                else {
                    searchParamText.setVisibility(View.GONE);

                    if(selectedItem.equals("Event Type")){
                        DatabaseReference types = db.getReference("eventtypes");
                        SearchSpinnerList.clear();
                        types.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot types : snapshot.getChildren()) {
                                    String type = types.child("name").getValue(String.class);
                                    SearchSpinnerList.add(type);
                                    adapter.notifyDataSetChanged();
                                }
                                searchAdapter = new ArrayAdapter<>(Event_Discovery.this, android.R.layout.simple_spinner_item , SearchSpinnerList);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                search_option_spinner.setAdapter(searchAdapter);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        //NOT WORKING
                        DatabaseReference clubs = db.getReference("clubs");
                        clubsDict = new HashMap<>();
                        SearchSpinnerList.clear();
                        clubs.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot types : snapshot.getChildren()) {
                                    String type = types.child("ClubName").getValue(String.class);
                                    if(type!=null){
                                        SearchSpinnerList.add(type);
                                        clubsDict.put(type, types.getKey().toString());
                                    }
                                }
                                searchAdapter = new ArrayAdapter<>(Event_Discovery.this, android.R.layout.simple_spinner_item , SearchSpinnerList);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                search_option_spinner.setAdapter(searchAdapter);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    search_option_spinner.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Search button (not working)
        Button Search =  findViewById(R.id.searchvalueBtn);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList.clear();
                //DEBUG FOR MULTIPLES ISSUE
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String club = snapshot.getKey().toString();
                        if(propertySelect.getSelectedItem().toString().equals("Club Name")){
                            club = clubsDict.get(search_option_spinner.getSelectedItem().toString());
                        }
                        DatabaseReference clubref = FirebaseDatabase.getInstance().getReference("clubs/"+club+"/events");
                        //NOT WORKING
                        String finalClub = club;
                        clubref.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String name = snapshot.child("name").getValue(String.class);
                                String type = snapshot.child("eventtype").getValue(String.class);
                                String level = snapshot.child("level").getValue(String.class);
                                String location = snapshot.child("location").getValue(String.class);
                                String pace = snapshot.child("pace").getValue(String.class);
                                Integer limit = snapshot.child("participantLimit").getValue(Integer.class);
                                String fee = snapshot.child("registrationFee").getValue(String.class);

                                if (propertySelect.getSelectedItem().toString().equals("Event Type")) {
                                    if(type.equals(search_option_spinner.getSelectedItem().toString())){
                                        ArrayList.add(new eventlistitem(name, type, location, level, pace, limit, fee, finalClub));
                                        adapter.notifyDataSetChanged();
                                    }
                                } else if (propertySelect.getSelectedItem().toString().equals("Event Name")) {
                                    if(name.equals(searchParamText.getText().toString())){
                                        ArrayList.add(new eventlistitem(name, type, location, level, pace, limit, fee, finalClub));
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    ArrayList.add(new eventlistitem(name, type, location, level, pace, limit, fee, finalClub));
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            //fix all on child changed and other data cases
                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                //String name = snapshot.child("name").getValue(String.class);
                                //String type = snapshot.child("eventtype").getValue(String.class);
                                //String level = snapshot.child("level").getValue(String.class);
                                //String location = snapshot.child("location").getValue(String.class);
                                //String pace = snapshot.child("pace").getValue(String.class);
                                //Integer limit = snapshot.child("participantLimit").getValue(Integer.class);
                                //String fee = snapshot.child("registrationFee").getValue(String.class);
                                //Club = snapshot.getKey();
                                //for (int i = 0; i < ArrayList.size(); i++) {
                                //    if(ArrayList.get(i).getName().equals(name)){
                                //        ArrayList.remove(i);
                                //    }
                                //}
                                //ArrayList.add(new eventlistitem(name, type, location, level, pace, limit, fee, Club));
                                //adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                //String name = snapshot.child("name").getValue(String.class);
                                //for (int i = 0; i < ArrayList.size(); i++) {
                                //    if(ArrayList.get(i).getName().equals(name)){
                                //        ArrayList.remove(i);
                                //    }
                                //}
                                //adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        eventslist.setItemsCanFocus(true);
        eventslist.setOnItemClickListener(new AdapterView.OnItemClickListener() { //set selected user based on user list click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventlistitem name = (eventlistitem) parent.getItemAtPosition(position);
                Selected = name.getName();
                Club = name.getClubusrname();
            }
        });
        //DB List Stuff over


        //Register for event (not working)
        Button Register =  findViewById(R.id.RegEventBtn);
        Register.setOnClickListener(new View.OnClickListener() { //delete button functionality
            @Override
            public void onClick(View v) {
                DatabaseReference eventStatus = db.getReference("users/"+LoginPage.Username+"/events/"+Selected);
                eventStatus.setValue("Pending");
                DatabaseReference eventUpdate = db.getReference("clubs/"+Club+"/events/"+Selected+"/participants/"+LoginPage.Username);
                eventUpdate.setValue("Pending");
            }
        });

        //Back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
    }
}