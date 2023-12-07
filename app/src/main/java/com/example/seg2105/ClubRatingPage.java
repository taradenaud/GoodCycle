package com.example.seg2105;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class ClubRatingPage extends AppCompatActivity {

    private ListView clubRatingListView;
    private Spinner ratingSpinner;
    private Button submitRatingButton;
    private ClubRatingAdapter clubRatingAdapter;
    private List<ClubRating> clubRatingList = new ArrayList<>();
    private Map<String, String> ClubDict;
    private String selectedClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_club);

        clubRatingListView = findViewById(R.id.clubRatingList);

        submitRatingButton = findViewById(R.id.clubRatingBtn);

        clubRatingAdapter = new ClubRatingAdapter(this, R.layout.rating_list_item, clubRatingList);
        clubRatingListView.setAdapter(clubRatingAdapter);

        ClubDict = new HashMap<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clubs");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String ClubUsrName = snapshot.getKey();
                String ClubDisplayName = snapshot.child("ClubName").getValue(String.class);
                ClubDict.put(ClubDisplayName, ClubUsrName);
                int count = 0;
                float average = 0;
                for (DataSnapshot rating : snapshot.child("rating").getChildren()){
                    count = count+1;
                    String[] feedback = rating.getValue(String.class).split(",");
                    float individual = Float.valueOf(feedback[0]);
                    average = average + individual;
                }
                if(count>0){
                    average = (average/count);
                }
                clubRatingList.add(new ClubRating(ClubDisplayName, average));
                clubRatingAdapter.notifyDataSetChanged();
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

        clubRatingListView.setItemsCanFocus(true);

        clubRatingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClubRating club = (ClubRating) parent.getItemAtPosition(position);
                selectedClub = ClubDict.get(club.getClubName());
            }
        });

        RatingBar ratingBar = findViewById(R.id.ClubRatingBar);
        EditText comments = findViewById(R.id.CommentsMultiline);

        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String feedback = String.valueOf(rating)+","+comments.getText().toString();
                DatabaseReference addedRating = ref.child(selectedClub+"/rating/"+LoginPage.Username);

                addedRating.setValue(feedback);
            }
        });

        Button back = (Button) findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
    }


}

