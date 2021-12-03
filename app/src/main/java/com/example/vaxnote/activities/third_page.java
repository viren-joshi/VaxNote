package com.example.vaxnote.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vaxnote.R;

public class third_page extends AppCompatActivity {
    Button SignIn,SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);
        SignIn = findViewById(R.id.signInButton);
        SignUp = findViewById(R.id.buttonSignUp);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(third_page.this,SignInActivity.class);
                startActivity(i);
                finish();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(third_page.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}