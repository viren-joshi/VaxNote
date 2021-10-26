package com.example.vaxnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    EditText userloginEmail,userloginPassword;
    TextView goToSignUp;
    Button SignIn;
    Intent open_main_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing the Views
        setContentView(R.layout.activity_sign_in);
        SignIn = findViewById(R.id.signIn);
        userloginEmail = findViewById(R.id.userLoginEmail);
        userloginPassword = findViewById(R.id.userLoginPassword);
        goToSignUp = findViewById(R.id.goToSignUp);

        //What happens on clicking the button happens here -
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_signUp_activity = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(open_signUp_activity);
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userLoginEmail = userloginEmail.getText().toString();
                String userLoginPassword = userloginPassword.getText().toString();
                Log.d("User_Login", userLoginEmail + " " + userLoginPassword);
                Toast.makeText(SignInActivity.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                open_main_activity = new Intent(SignInActivity.this, MainActivity.class);
            }
        });
    }
}