package com.example.vaxnote.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vaxnote.R;

public class first_page extends AppCompatActivity {
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        nextButton = findViewById(R.id.next1);
        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(first_page.this,second_page.class);
            startActivity(intent);
        });
    }
}