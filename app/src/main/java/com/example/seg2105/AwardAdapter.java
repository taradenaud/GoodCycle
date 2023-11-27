package com.example.seg2105;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class AwardAdapter extends ArrayAdapter<awardlistitem> {

    public AwardAdapter(Context context, int resource, List<awardlistitem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        awardlistitem awardListItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.award_list_item, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvAward = convertView.findViewById(R.id.awardNameEditText);
        TextView tvAwardDetails = convertView.findViewById(R.id.awardDetailEditText);
        TextView tvResults = convertView.findViewById(R.id.results);

        tvName.setText(awardListItem.getName());
        tvAward.setText(awardListItem.getAwardName());
        tvAwardDetails.setText(awardListItem.getAwardDetails());
        tvResults.setText(awardListItem.getResults());

        return convertView;
    }
}

