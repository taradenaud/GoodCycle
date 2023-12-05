package com.example.seg2105;

public class ClubRating {
    private String clubName;
    private String Comment;
    private float rating; // Assume this is the club rating

    public ClubRating(String clubName, float rating) {
        this.clubName = clubName;
        this.rating = rating;
    }
    public ClubRating(String clubName, float rating, String comment){
        this.clubName = clubName;
        this.rating = rating;
        this.Comment = comment;
    }

    // Getters
    public String getClubName() {
        return clubName;
    }

    public float getRating() {
        return rating;
    }
    public String getComment(){return Comment;}
}

