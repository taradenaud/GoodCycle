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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClubAwardDashboard extends AppCompatActivity {

    private ListView awardsListView;
    private List<String> events = new ArrayList<String>();
    private List<awardlistitem> awardList = new ArrayList<awardlistitem>();
    private AwardAdapter adapter;
    private DatabaseReference ref;
    private String eventname;
    private String Selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_award_dashboard);

        ref = FirebaseDatabase.getInstance().getReference("clubs/"+LoginPage.Username+"/events");
        awardsListView = (ListView) findViewById(R.id.awardsList);
        adapter = new AwardAdapter(ClubAwardDashboard.this, R.layout.award_list_item, awardList);
        awardsListView.setAdapter(adapter);
        Spinner selectedEvent = (Spinner) findViewById(R.id.eventSelector); //Selection identifier at top of page

        FirebaseDatabase.getInstance().getReference("clubs/"+LoginPage.Username+"/events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot event: snapshot.getChildren()){
                    String EventName = event.getKey().toString();
                    events.add(EventName);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClubAwardDashboard.this, android.R.layout.simple_spinner_item , events);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectedEvent.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selectedEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventname = events.get(selectedEvent.getSelectedItemPosition()).toString();
                ref.child(eventname+"/results").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String name = snapshot.getKey();
                        String[] details = snapshot.getValue(String.class).split(",");
                        String awardName = details[0];
                        String awardDesc = details[1];
                        String place = details[2];
                        awardList.add(new awardlistitem(name, awardName, awardDesc, place, eventname));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String name = snapshot.getKey();
                        String[] details = snapshot.getValue(String.class).split(",");
                        String awardName = details[0];
                        String awardDesc = details[1];
                        String place = details[2];
                        for (int i = 0; i < awardList.size(); i++) {
                            if(awardList.get(i).getName().equals(name)){
                                awardList.remove(i);
                            }
                        }
                        awardList.add(new awardlistitem(name, awardName, awardDesc, place, eventname));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        for (int i = 0; i < awardList.size(); i++) {
                            if(awardList.get(i).getName().equals(snapshot.getKey())){
                                awardList.remove(i);
                            }
                        }
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        awardsListView.setItemsCanFocus(true);

        EditText pUsrName = findViewById(R.id.participantUserName);
        EditText resultsEditText = findViewById(R.id.resultsEditText);
        EditText awardNameEditText = findViewById(R.id.awardNameEditText);
        EditText awardDetailEditText = findViewById(R.id.awardDetailEditText);
        awardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //set selected user based on user list click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                awardlistitem name = (awardlistitem) parent.getItemAtPosition(position);
                Selected = name.getName();
                pUsrName.setText(Selected);
                resultsEditText.setText(name.getResults());
                awardNameEditText.setText(name.getAwardName());
                awardDetailEditText.setText(name.getAwardDetails());
            }
        });



        Button saveResultsAndAwardsButton = findViewById(R.id.saveResultsAndAwardsButton);
        saveResultsAndAwardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered data
                String entry = awardNameEditText.getText().toString() + "," + awardDetailEditText.getText().toString() + "," + resultsEditText.getText().toString();
                DatabaseReference newaward = FirebaseDatabase.getInstance().getReference("clubs/"+LoginPage.Username+"/events/"+eventname+"/results/"+Selected);
                newaward.setValue(entry);
                // Clear the input fields after saving
                pUsrName.setText("");
                resultsEditText.setText("");
                awardNameEditText.setText("");
                awardDetailEditText.setText("");

                // Optionally, refresh the list or handle UI updates
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

