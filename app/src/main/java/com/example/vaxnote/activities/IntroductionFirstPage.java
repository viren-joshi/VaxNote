package com.example.vaxnote.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.vaxnote.R;

public class IntroductionFirstPage extends AppCompatActivity {
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        nextButton = findViewById(R.id.next1);
        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(IntroductionFirstPage.this, IntroductionSecondPage.class);
            startActivity(intent);
        });
    }
}