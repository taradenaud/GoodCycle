package com.example.seg2105;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClubRatingPage extends AppCompatActivity {

    private ListView clubRatingListView;
    private Spinner ratingSpinner;
    private Button submitRatingButton;
    private ClubRatingAdapter clubRatingAdapter;
    private List<ClubRating> clubRatingList = new ArrayList<>();
    private String selectedClub;
    private String selectedRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_club);

        clubRatingListView = findViewById(R.id.clubRatingList);
        ratingSpinner = findViewById(R.id.ratingSpinner);
        submitRatingButton = findViewById(R.id.clubRatingBtn);

        setupListView();
        setupRatingSpinner();
        setupSubmitButton();

        loadClubRatings(); // This should load the clubs into clubRatingList
    }

    private void setupListView() {
        clubRatingAdapter = new ClubRatingAdapter(this, R.layout.reg_status_list_item, clubRatingList);
        clubRatingListView.setAdapter(clubRatingAdapter);

        clubRatingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClubRating clubRating = clubRatingList.get(position);
                selectedClub = clubRating.getClubName(); // Assuming club name is unique
            }
        });
    }

    private void setupRatingSpinner() {
        ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(this,
                R.array.ratings_array, android.R.layout.simple_spinner_item);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(ratingAdapter);

        ratingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRating = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no selection is made, if necessary
            }
        });
    }

    private void setupSubmitButton() {
        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedClub != null && selectedRating != null) {
                    updateClubRating(selectedClub, selectedRating);
                } else {
                    Toast.makeText(ClubRatingPage.this, "Please select a club and a rating", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateClubRating(String clubId, String rating) {
        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("clubs")
                .child(clubId).child("rating").child(LoginPage.Username);

        ratingRef.setValue(rating)
                .addOnSuccessListener(aVoid -> Toast.makeText(ClubRatingPage.this, "Rating updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ClubRatingPage.this, "Failed to update rating", Toast.LENGTH_SHORT).show());
    }
    private void loadClubRatings() {
        DatabaseReference clubsRef = FirebaseDatabase.getInstance().getReference("clubs");

        clubsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clubRatingList.clear(); // Clear the existing list

                for (DataSnapshot clubSnapshot : dataSnapshot.getChildren()) {
                    String clubName = clubSnapshot.child("ClubName").getValue(String.class);

                    // Assuming the average rating is stored directly under the 'rating' node.
                    // If the structure is different, you'll need to adjust this logic.
                    DataSnapshot ratingSnapshot = clubSnapshot.child("rating");
                    float totalRating = 0;
                    int ratingCount = 0;

                    for (DataSnapshot userRating : ratingSnapshot.getChildren()) {
                        Integer rating = userRating.getValue(Integer.class);
                        if (rating != null) {
                            totalRating += rating;
                            ratingCount++;
                        }
                    }

                    // Calculate the average rating
                    String averageRating = (ratingCount > 0) ? String.format("%.1f", totalRating / ratingCount) : "No Ratings";

                    clubRatingList.add(new ClubRating(clubName, averageRating));
                }

                clubRatingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

