package com.example.seg2105;

public class ClubRating {
    private String clubName;
    private String rating; // Assume this is the club rating

    public ClubRating(String clubName, String rating) {
        this.clubName = clubName;
        this.rating = rating;
    }

    // Getters
    public String getClubName() {
        return clubName;
    }

    public String getRating() {
        return rating;
    }
}

