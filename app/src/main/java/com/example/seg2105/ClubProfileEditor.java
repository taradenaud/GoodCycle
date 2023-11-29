package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClubProfileEditor extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    private Boolean all_good = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_profile_editor);

        EditText clubName = (EditText) findViewById(R.id.ClubName);
        EditText InstagramLink = (EditText) findViewById(R.id.InstagramLink);
        EditText ContactNumber = (EditText) findViewById(R.id.ContactNumber);
        EditText ContactName = (EditText) findViewById(R.id.ContactName);

        ref = database.getReference("clubs/"+LoginPage.Username);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot details : snapshot.getChildren() ){
                    if(details.exists()){
                        String detail = details.getKey().toString();
                        if(detail.equals("ClubName")) {
                            clubName.setText(details.getValue().toString());
                        } else if (detail.equals("InstagramLink")) {
                            InstagramLink.setText(details.getValue().toString());
                        }else if (detail.equals("ContactNumber")) {
                            ContactNumber.setText(details.getValue().toString());
                        }else if (detail.equals("ContactName")) {
                            ContactName.setText(details.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button save = (Button) findViewById(R.id.buttonSave);
        TextView InstalinkVal = (TextView) findViewById(R.id.Instatextval);
        TextView ContactNumVal = (TextView) findViewById(R.id.ContactNumVal);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_good = true;
                if(String.valueOf(InstagramLink.getText()).trim().length() == 0){
                    InstalinkVal.setText("Mandatory Field Please Add a Link");
                    all_good = false;
                } else {InstalinkVal.setText("");}
                if(String.valueOf(ContactNumber.getText()).trim().length() == 0 ){
                    ContactNumVal.setText("Mandatory Field Please Add a Phone Number");
                    all_good = false;
                } else{ContactNumVal.setText("");}

                if(all_good == true){
                    DatabaseReference CLUBNAME = database.getReference("clubs/"+LoginPage.Username+"/ClubName");
                    DatabaseReference INSTALINK = database.getReference("clubs/"+LoginPage.Username+"/InstagramLink");
                    DatabaseReference PHONENUM = database.getReference("clubs/"+LoginPage.Username + "/ContactNumber");
                    DatabaseReference CONTACTNAME = database.getReference("clubs/"+LoginPage.Username + "/ContactName");

                    CLUBNAME.setValue(clubName.getText().toString());
                    INSTALINK.setValue(InstagramLink.getText().toString());
                    PHONENUM.setValue(ContactNumber.getText().toString());
                    if(String.valueOf(ContactName.getText()).trim().length() == 0){
                        CONTACTNAME.setValue(ContactName.getText().toString());
                    }

                }
            }
        });

        Button back = (Button) findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will navigate back to the previous page
            }
        });
    }
}