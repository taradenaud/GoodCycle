package com.example.seg2105;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.List;
public class ClubRatingAdapter extends ArrayAdapter<ClubRating> {

    private int layoutResourceId;
    public ClubRatingAdapter(Context context, int resource, List<ClubRating> items) {
        super(context, resource, items);
        this.layoutResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResourceId, parent, false);
        }

        ClubRating clubRating = getItem(position);

        TextView nameView = convertView.findViewById(R.id.name);
        RatingBar ratingView = convertView.findViewById(R.id.ratingBar);
        TextView comments = convertView.findViewById(R.id.comments);

        nameView.setText(clubRating.getClubName());
        ratingView.setRating(clubRating.getRating());
        if(clubRating.getComment()!= null){
            comments.setText(clubRating.getComment());
        }else {comments.setText("");}

        return convertView;
    }
}


