package com.example.seg2105;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
public class EventWithStatusAdapter extends ArrayAdapter<EventWithStatus> {

    public EventWithStatusAdapter(Context context, int resource, List<EventWithStatus> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reg_status_list_item, parent, false);
        }

        EventWithStatus event = getItem(position);

        TextView nameView = convertView.findViewById(R.id.name);
        TextView regStatusView = convertView.findViewById(R.id.regStatus);

        nameView.setText(event.getEventName());
        regStatusView.setText("Status: " + event.getUserStatus());

        return convertView;
    }
}

