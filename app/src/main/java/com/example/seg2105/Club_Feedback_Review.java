package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Club_Feedback_Review extends AppCompatActivity {
    private ListView feedbackList;
    private ClubRatingAdapter clubRatingAdapter;
    private List<ClubRating> clubRatingList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_feedback_review);

        feedbackList = findViewById(R.id.feedback);

        clubRatingAdapter = new ClubRatingAdapter(this, R.layout.rating_list_item, clubRatingList);
        feedbackList.setAdapter(clubRatingAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("clubs/"+LoginPage.Username+"/rating");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String Name = snapshot.getKey();
                String feedback[] = snapshot.getValue(String.class).split(",",2);
                float rating = Float.valueOf(feedback[0]);
                String comments = feedback[1];
                clubRatingList.add(new ClubRating(Name, rating, comments));
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
    }
}