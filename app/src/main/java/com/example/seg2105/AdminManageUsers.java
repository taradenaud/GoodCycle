package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdminManageUsers extends AppCompatActivity {

    private ListView UsersList;
    private List<Userlistitem> ArrayList = new ArrayList<>();
    private UserAdapter adapter;
    private DatabaseReference ref;
    private String Selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_users);

        ref = FirebaseDatabase.getInstance().getReference("users");
        adapter = new UserAdapter(this, R.layout.user_list_item, ArrayList);
        UsersList = findViewById(R.id.UsersListView);
        UsersList.setAdapter(adapter);

        EditText selectedUser = findViewById(R.id.selectedusrtext); //Selection identifier at top of page
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Called when loading list
                String user = snapshot.child("username").getValue(String.class);
                String role = snapshot.child("role").getValue(String.class);
                if(!role.equals("Admin")){
                    ArrayList.add(new Userlistitem(user,role));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Refresh list as something was changed
                if(ArrayList.size() > 0){
                    ArrayList.clear();
                }
                String user = snapshot.child("username").getValue(String.class);
                String role = snapshot.child("role").getValue(String.class);
                if(!role.equals("Admin")){
                    ArrayList.add(new Userlistitem(user,role));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { //Something is Deleted from Database, all that needs to happen is remove deleted item from arraylist of users
                for (int i = 0; i < ArrayList.size(); i++){ //Not efficient needs optimization
                    if(ArrayList.get(i).getUsername().equals(snapshot.child("username").getValue(String.class))){
                        ArrayList.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
        }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        UsersList.setItemsCanFocus(true);
        UsersList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //set selected user based on user list click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Userlistitem user = (Userlistitem) parent.getItemAtPosition(position);
                Selected = user.getUsername();
                selectedUser.setText(Selected);
            }
        });

        Button DeleteUsr = (Button) findViewById(R.id.DeleteUsr);
        DeleteUsr.setOnClickListener(new View.OnClickListener() { //delete button functionality
            @Override
            public void onClick(View v) {
                ref.child(Selected).removeValue();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });


    }

}