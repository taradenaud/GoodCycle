package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegistrationManagement extends AppCompatActivity {

    private List<String> events = new ArrayList<>();
    private List<Userlistitem> users = new ArrayList<>();
    private UserAdapter adapter;
    private DatabaseReference ref;
    private FirebaseDatabase dbRef = FirebaseDatabase.getInstance();
    private int spots;
    private String Selected;
    private String eventName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_management);

        Spinner event = findViewById(R.id.EventsSpinner);
        TextView spotsleft = findViewById(R.id.SpacesText);
        ListView userlist = findViewById(R.id.UserList);
        adapter = new UserAdapter(this, R.layout.user_list_item, users);
        userlist.setAdapter(adapter);


        ref = FirebaseDatabase.getInstance().getReference("clubs");
        ref.child(LoginPage.Username+"/events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot event: snapshot.getChildren()){
                    String EventName = event.getKey();
                    events.add(EventName);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(RegistrationManagement.this, android.R.layout.simple_spinner_item , events);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                event.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventName = events.get(event.getSelectedItemPosition());
                ref.child(LoginPage.Username+"/events/"+eventName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        spots = snapshot.child("participantLimit").getValue(Integer.class);

                        ref.child(LoginPage.Username+"/events/"+eventName+"/participants").addChildEventListener(new ChildEventListener() {

                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String username = snapshot.getKey();
                                boolean flag = true;
                                for (int i = 0; i < users.size(); i++) {
                                    if(username.equals(users.get(i).getUsername())){
                                        flag = false;
                                    }
                                }
                                if(flag){
                                    String status = snapshot.getValue(String.class);
                                    users.add(new Userlistitem(username, status));
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String username = snapshot.getKey();
                                for (int i = 0; i < users.size(); i++) {
                                    if(username.equals(users.get(i).getUsername())){
                                        users.remove(i);
                                    }
                                }
                                users.add(new Userlistitem(username, "Accepted"));
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                String username = snapshot.getKey();
                                for (int i = 0; i < users.size(); i++) {
                                    if(username.equals(users.get(i).getUsername())){
                                        users.remove(i);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                spotsleft.setText("Participant Limit: "+spots);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        userlist.setItemsCanFocus(true);

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Userlistitem user = (Userlistitem) parent.getItemAtPosition(position);
                Selected = user.getUsername();
            }
        });

        Button Accept = findViewById(R.id.AcceptBtn);
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userToAccept = dbRef.getReference("clubs/"+LoginPage.Username+"/events/"+eventName+"/participants/"+Selected);
                userToAccept.setValue("Accepted");
                userToAccept = dbRef.getReference("users/"+Selected+"/events/"+eventName);
                userToAccept.setValue("Accepted");
            }
        });

        Button Deny = findViewById(R.id.DenyBtn);
        Deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usertoReject = dbRef.getReference("clubs/"+LoginPage.Username+"/events/"+eventName+"/participants/"+Selected);
                usertoReject.setValue("Denied");
                usertoReject = dbRef.getReference("users/"+Selected+"/events/"+eventName);
                usertoReject.setValue("Denied");
            }
        });

        Button back = findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
    }
}