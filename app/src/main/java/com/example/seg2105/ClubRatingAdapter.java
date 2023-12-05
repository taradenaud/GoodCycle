package com.example.seg2105;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
public class ClubRatingAdapter extends ArrayAdapter<ClubRating> {

    public ClubRatingAdapter(Context context, int resource, List<ClubRating> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reg_status_list_item, parent, false);
        }

        ClubRating clubRating = getItem(position);

        TextView nameView = convertView.findViewById(R.id.name);
        TextView ratingView = convertView.findViewById(R.id.clubRating);

        nameView.setText("Club: " + clubRating.getClubName());
        ratingView.setText("Club Rating: " + clubRating.getRating());

        return convertView;
    }
}


