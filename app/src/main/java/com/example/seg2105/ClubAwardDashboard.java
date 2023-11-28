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

public class ClubAwardDashboard extends AppCompatActivity {

    private ListView awardsListView;
    private List<awardlistitem> awardList = new ArrayList<>();
    private AwardAdapter adapter;
    private DatabaseReference ref;

    private String Selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_award_dashboard);

        ref = FirebaseDatabase.getInstance().getReference("awards");
        adapter = new AwardAdapter(this, R.layout.award_list_item, awardList);
        awardsListView.setAdapter(adapter);
        EditText selectedEvent = findViewById(R.id.SelectedEvent); //Selection identifier at top of page


        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Called when loading list
                String name = snapshot.child("name").getValue(String.class);
                String awardName = snapshot.child("awardName").getValue(String.class);
                String awardDetails = snapshot.child("awardDetails").getValue(String.class);
                String results = snapshot.child("results").getValue(String.class);

                awardList.add(new awardlistitem(name, awardName, awardDetails, results));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Refresh list as something was changed
                if (awardList.size() > 0) {
                    awardList.clear();
                }
                String name = snapshot.child("name").getValue(String.class);
                String awardName = snapshot.child("awardName").getValue(String.class);
                String awardDetails = snapshot.child("awardDetails").getValue(String.class);
                String results = snapshot.child("results").getValue(String.class);

                awardList.add(new awardlistitem(name, awardName, awardDetails, results));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { //Something is Deleted from Database, all that needs to happen is remove deleted item from arraylist of users
                for (int i = 0; i < awardList.size(); i++) { //Not efficient needs optimization
                    if (awardList.get(i).getName().equals(snapshot.child("name").getValue(String.class))) {
                        awardList.remove(i);
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
                String results = resultsEditText.getText().toString();
                String awardName = awardNameEditText.getText().toString();
                String awardDetails = awardDetailEditText.getText().toString();

                // Create a new AwardListItem object
                awardlistitem newAward = new awardlistitem(Selected, awardName, awardDetails, results);

                // Push the new award to Firebase or update the existing one
                if (!Selected.isEmpty()) {
                    ref.child(Selected).setValue(newAward);
                } else {
                    // Handle the case where no event is selected
                    // For example, push as a new child
                    ref.push().setValue(newAward);
                }

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

