package com.example.seg2105;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class UserAdapter extends ArrayAdapter<Userlistitem> {
    private int layoutResourceId;

    public UserAdapter(Context context, int layoutResourceId, List<Userlistitem> data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        UserlistitemHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserlistitemHolder();
            holder.Username = row.findViewById(R.id.Username);
            holder.Role = row.findViewById(R.id.Role);

            row.setTag(holder);
        } else {
            holder = (UserlistitemHolder) row.getTag();
        }

        Userlistitem item = getItem(position);
        if (item != null) {
            holder.Username.setText(item.getUsername());
            holder.Role.setText(item.getRole());
        }

        return row;
    }

    static class UserlistitemHolder {
        TextView Username;
        TextView Role;
    }
}
