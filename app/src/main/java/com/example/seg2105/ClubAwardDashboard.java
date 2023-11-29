package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClubAwardDashboard extends AppCompatActivity {

    private ListView awardsListView;
    private List<awardlistitem> awardList = new ArrayList<awardlistitem>();
    private AwardAdapter adapter;
    private DatabaseReference ref;

    private String Selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_award_dashboard);

        ref = FirebaseDatabase.getInstance().getReference("clubs/"+LoginPage.Username+"/events");
        awardsListView = (ListView) findViewById(R.id.awardsList);
        adapter = new AwardAdapter(this, R.layout.user_list_item, awardList);
        awardsListView.setAdapter(adapter);
        EditText selectedEvent = findViewById(R.id.SelectedEvent); //Selection identifier at top of page


        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Called when loading list
                String eventname = snapshot.getKey();
                DatabaseReference results = snapshot.child("results").getRef();
                results.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Pattern alphabet = Pattern.compile("[A-Za-z]");

                        Matcher matcher = alphabet.matcher(snapshot.getKey());
                        if(matcher.find()){
                         String name = snapshot.getKey();
                         String[] details = snapshot.getValue(String.class).split(",");
                         String awardName = details[0];
                         String awardDesc = details[1];
                         String place = details[2];
                         awardList.add(new awardlistitem(name, awardName, awardDesc, place, eventname));
                         adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Refresh list as something was changed

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { //Something is Deleted from Database, all that needs to happen is remove deleted item from arraylist of users

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        awardsListView.setItemsCanFocus(true);
        awardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //set selected user based on user list click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                awardlistitem name = (awardlistitem) parent.getItemAtPosition(position);
                Selected = name.getName();
                selectedEvent.setText(Selected);
            }
        });

        EditText resultsEditText = findViewById(R.id.resultsEditText);
        EditText awardNameEditText = findViewById(R.id.awardNameEditText);
        EditText awardDetailEditText = findViewById(R.id.awardDetailEditText);

        Button saveResultsAndAwardsButton = findViewById(R.id.saveResultsAndAwardsButton);
        saveResultsAndAwardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered data
                String awardName = awardNameEditText.getText().toString();

                // Create a new AwardListItem object



                // Clear the input fields after saving
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

