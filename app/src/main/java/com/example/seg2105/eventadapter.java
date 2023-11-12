package com.example.seg2105;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class eventadapter extends ArrayAdapter<eventlistitem> {
    private int layoutResourceId;

    public eventadapter(Context context, int layoutResourceId, List<eventlistitem> data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EventListItemHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EventListItemHolder();
            holder.name = row.findViewById(R.id.name);  // Replace with the actual ID of your TextView for the name
            holder.type = row.findViewById(R.id.type);  // Replace with the actual ID of your TextView for the type
            holder.location = row.findViewById(R.id.location);  // Replace with the actual ID of your TextView for the location
            holder.level = row.findViewById(R.id.difflevel);  // Replace with the actual ID of your TextView for the level
            holder.pace = row.findViewById(R.id.racepace);  // Replace with the actual ID of your TextView for the pace
            holder.participantLimit = row.findViewById(R.id.partlimit);  // Replace with the actual ID of your TextView for participant limit
            holder.regfee = row.findViewById(R.id.regfee);  // Replace with the actual ID of your TextView for the registration fee

            row.setTag(holder);
        } else {
            holder = (EventListItemHolder) row.getTag();
        }

        eventlistitem item = getItem(position);
        if (item != null) {
            holder.name.setText(item.getName());
            holder.type.setText(item.getType());
            holder.location.setText(item.getLocation());
            holder.level.setText(item.getLevel());
            holder.pace.setText(item.getPace());
            holder.participantLimit.setText(item.getParticipantLimit());
            holder.regfee.setText(item.getRegfee());
        }

        return row;
    }

    static class EventListItemHolder {
        TextView name;
        TextView type;
        TextView location;
        TextView level;
        TextView pace;
        TextView participantLimit;
        TextView regfee;
    }
}
