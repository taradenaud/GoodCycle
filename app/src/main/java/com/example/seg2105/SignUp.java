package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    private Spinner spinner;
    private DatabaseReference databaseReference;
    private Boolean all_good = true;

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
        EditText emailText = findViewById(R.id.LoginUsername);
        EditText UserName = findViewById(R.id.username);
        EditText pwdText = findViewById(R.id.LoginPassword);
        TextView emailTextval = findViewById(R.id.emailValidation);
        TextView UserNameval =  findViewById(R.id.UsernameValidation);
        TextView pwdTextval =  findViewById(R.id.passwordValidation);

        Spinner userRoleSelect = (Spinner) findViewById(R.id.user_selection_spinner);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String role = userRoleSelect.getSelectedItem().toString();
                String username = String.valueOf(UserName.getText());
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                if(String.valueOf(emailText.getText()).trim().length() == 0){
                    emailTextval.setText("Please Enter A Valid Email");
                    all_good = false;
                }else{emailTextval.setText("");}
                if(String.valueOf(UserName.getText()).trim().length() == 0){
                    UserNameval.setText("Please Enter A Username");
                    all_good = false;
                } else {
                     DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("users/"+username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                all_good = false;
                                UserNameval.setText("Username already taken");
                            }
                            else{
                                UserNameval.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                if(String.valueOf(pwdText.getText()).trim().length() == 0){
                    pwdTextval.setText("Please Enter A Pasword");
                    all_good = false;
                }else{pwdTextval.setText("");}
                if(all_good == true) {
                    DatabaseReference newUserNameRef = database.getReference("users/" + username + "/username");
                    DatabaseReference newUserRoleRef = database.getReference("users/" + username + "/role");
                    DatabaseReference newUserEmailRef = database.getReference("users/" + username + "/email");
                    DatabaseReference newUserPasswordRef = database.getReference("users/" + username + "/password");

                    newUserEmailRef.setValue(username);
                    newUserNameRef.setValue(username);
                    newUserEmailRef.setValue(String.valueOf(emailText.getText()));
                    newUserPasswordRef.setValue(String.valueOf(pwdText.getText()));
                    newUserRoleRef.setValue(role);

                    startActivity(new Intent(SignUp.this, LoginPage.class));
                }
            }
        });
        }
}