package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    private static boolean flag = false;
    private static void setflag(){
        flag = true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Button LogIn = (Button) findViewById(R.id.btn_reg);
        EditText usernameText = findViewById(R.id.LoginUsername);
        EditText pwdText = findViewById(R.id.LoginPassword);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(usernameText.getText());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("users/"+username+"/password");
                String password = String.valueOf(pwdText.getText());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        if(password.equals(value)){
                            setflag();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
                ref = database.getReference("users/"+username+"/role");
                if (flag == true){
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String value = dataSnapshot.getValue(String.class);
                            if(value.equals("Admin")){
                                startActivity(new Intent( LoginPage.this, admin_dashboard.class));
                            }
                            else if(value.equals("Participant")){
                                startActivity(new Intent( LoginPage.this, welcome_participant.class));
                            }
                            else if(value.equals("Club")){
                                startActivity(new Intent( LoginPage.this, welcome_clubOwner.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                }
            }
        });
    }
}