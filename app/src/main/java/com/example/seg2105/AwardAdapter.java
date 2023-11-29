package com.example.seg2105;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class AwardAdapter extends ArrayAdapter<awardlistitem> {
    private int layoutResourceId;
    public AwardAdapter(Context context, int resource, List<awardlistitem> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AwardlistItemHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AwardlistItemHolder();
            holder.Name = row.findViewById(R.id.name);
            holder.Award = row.findViewById(R.id.awardNameEditText);
            holder.Details = row.findViewById(R.id.AwardDetails);
            holder.Results = row.findViewById(R.id.resultsText);
            holder.Event = row.findViewById(R.id.eventnametv);

            row.setTag(holder);
        }else{
            holder = (AwardlistItemHolder) row.getTag();
        }
        awardlistitem item = getItem(position);
        if(item != null){
            holder.Name.setText(item.getName());
            holder.Award.setText(item.getAwardName());
            holder.Details.setText(item.getAwardDetails());
            holder.Results.setText(item.getResults());
            holder.Event.setText(item.getEventName());
        }

        return row;
    }

    static class AwardlistItemHolder{
        TextView Name;
        TextView Award;
        TextView Details;
        TextView Results;

        TextView Event;
    }
}

