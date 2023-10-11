package com.example.seg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class role extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        Intent intent = getIntent();
        if (intent != null) {
            String newText = intent.getStringExtra("newText");
            if (newText != null) {
                TextView textView = findViewById(R.id.RoleText); // Replace with your TextView ID
                textView.setText(newText);
            }
        }
    }
}