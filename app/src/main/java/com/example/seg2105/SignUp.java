package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        // spinner for user selection in the sign up page
        spinner = findViewById(R.id.user_selection_spinner); //spinner var set up

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.User_selection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        // end of spinner stuff


        Button reg = (Button) findViewById(R.id.btn_reg);
        EditText emailText = findViewById(R.id.LoginEmail);
        EditText username = findViewById(R.id.username);
        EditText pwdText = findViewById(R.id.LoginPassword);
        String role;
        if(findViewById(R.id.chip).isSelected()){
            role = "Cycling Club";
        }
        else {
            role = "participant";
        }
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference newUserRoleRef = database.getReference("users/"+username+"/role");
                DatabaseReference newUserEmailRef = database.getReference("users/"+username+"/email");
                DatabaseReference newUserPasswordRef = database.getReference("users/"+username+"/password");

                newUserEmailRef.setValue(emailText);
                newUserPasswordRef.setValue(pwdText);
                newUserRoleRef.setValue(role);
            }
        });
        }
}