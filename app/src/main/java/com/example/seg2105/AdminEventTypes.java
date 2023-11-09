package com.example.seg2105;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminEventTypes extends AppCompatActivity {

    private ListView TypesList;
    private ArrayList<String> ArrayList = new ArrayList<>();
    private ArrayAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private String CurrentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_types);

        ref = FirebaseDatabase.getInstance().getReference("eventtypes");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList);
        TypesList = findViewById(R.id.TypeList);
        TypesList.setAdapter(adapter);

        EditText selectedtype = findViewById(R.id.currentName);//Selection identifier at top of page
        ref.addChildEventListener(new ChildEventListener() { //Bugs with display right now after modification (rename) Alter on Child changed

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Called when loading list
                String type = snapshot.child("name").getValue(String.class);
                ArrayList.add(type);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//Refresh list as something was changed
                for(int i =0; i<ArrayList.size(); i++){
                    if(ArrayList.get(i).equals(CurrentName)){
                        ArrayList.remove(i);
                    }
                }
                String type = snapshot.child("name").getValue(String.class);
                ArrayList.add(type);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { //Something is Deleted from Database, all that needs to happen is remove deleted item from arraylist of users
                for (int i = 0; i < ArrayList.size(); i++){ //Not efficient needs optimization
                    if(ArrayList.get(i).equals(snapshot.child("name").getValue(String.class))){
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
        TypesList.setItemsCanFocus(true);
        TypesList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //set selected user based on user list click
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String eventtype =  parent.getItemAtPosition(position).toString();
                CurrentName = eventtype;
                selectedtype.setText(CurrentName);
            }
        });

        Button deleteType = (Button) findViewById(R.id.DeleteType);
        deleteType.setOnClickListener(new View.OnClickListener() { //delete button functionality
            @Override
            public void onClick(View v) {
                ref.child(CurrentName).removeValue();
            }
        });

        Button renameType = (Button) findViewById(R.id.renametype);
        EditText newname = (EditText) findViewById(R.id.newname);
        renameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i<ArrayList.size(); i++){
                    if(ArrayList.get(i).equals(CurrentName)){
                        DatabaseReference renameref = database.getReference("eventtypes/"+ "type"+(i+1));
                        String updatedName = String.valueOf(newname.getText());
                        renameref.child("name").setValue(updatedName);
                    }
                }
            }
        });

        FloatingActionButton AddType = (FloatingActionButton) findViewById(R.id.AddTypeButton);

        AddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newtype = (EditText) findViewById(R.id.NewTypeText);
                String name = String.valueOf(newtype.getText());
                DatabaseReference newTypeNameRef = database.getReference("eventtypes/"+"type"+ (ArrayList.size()+1)+"/name");
                newTypeNameRef.setValue(name);
                newtype.setText("Add New Event Type");
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