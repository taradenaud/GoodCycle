package com.example.seg2105;

public class ClubRating {
    private String clubName;
    private float rating; // Assume this is the club rating

    public ClubRating(String clubName, float rating) {
        this.clubName = clubName;
        this.rating = rating;
    }

    // Getters
    public String getClubName() {
        return clubName;
    }

    public float getRating() {
        return rating;
    }
}

