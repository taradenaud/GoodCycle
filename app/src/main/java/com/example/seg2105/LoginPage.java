package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button LogIn = (Button) findViewById(R.id.btn_reg);
        EditText emailText = findViewById(R.id.LoginEmail);
        EditText pwdText = findViewById(R.id.LoginPassword);
        LogIn.setOnClickListener(new View.OnClickListener() {
            String emailstring = emailText.getText().toString();
            String pwdstring = pwdText.getText().toString();
            String entryPath = "users/"+emailstring;
            String rolestring;
            Boolean pwdt = false;

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Task<DataSnapshot> dataTask = databaseReference.child("users").child(emailstring).child("password").get();
            DataSnapshot dataSnapshot = dataTask.getResult();
            String password = dataSnapshot.getValue(String.class);
            @Override
            public void onClick(View v) {
                if(password.equals(pwdstring)){
                    Task<DataSnapshot> dataTask = databaseReference.child("users").child(emailstring).child("role").get();
                    DataSnapshot dataSnapshot = dataTask.getResult();
                    rolestring = dataSnapshot.getValue(String.class);
                    Intent intent = new Intent(LoginPage.this, role.class);
                    String newText = "You are Signed in as "+rolestring;
                    intent.putExtra("role", newText);
                    startActivity(intent);
                }
            }
        });
    }
}