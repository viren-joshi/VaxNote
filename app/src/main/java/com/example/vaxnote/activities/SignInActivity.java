package com.example.vaxnote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaxnote.R;
import com.example.vaxnote.classes.UserAuthentication;

public class SignInActivity extends AppCompatActivity {
    EditText userloginEmail,userloginPassword;
    TextView goToSignUp;
    Button SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing the Views
        setContentView(R.layout.activity_sign_in);
        SignIn = findViewById(R.id.signUpButton);
        userloginEmail = findViewById(R.id.userLoginEmail);
        userloginPassword = findViewById(R.id.userLoginPassword);
        goToSignUp = findViewById(R.id.goToSignUp);

        //What happens on clicking the button happens here -
        goToSignUp.setOnClickListener(v -> {
            Intent open_signUp_activity = new Intent(SignInActivity.this,SignUpActivity.class);
            startActivity(open_signUp_activity);
        });

        SignIn.setOnClickListener(v -> {
            String userLoginEmail = userloginEmail.getText().toString();
            String userLoginPassword = userloginPassword.getText().toString();
            UserAuthentication userAuthentication = new UserAuthentication(SignInActivity.this);
            userAuthentication.SignIn(userLoginEmail,userLoginPassword);
        });
    }

}
