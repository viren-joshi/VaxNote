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

public class SignUpActivity extends AppCompatActivity {
    EditText usersignUpName, usersignUpEmail, userpassword, userconfirmPassword;
    Button signUpButton;
    TextView gotoSignIn;
    Intent open_main_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Initializing the views -
        usersignUpName = findViewById(R.id.userSignUpName);
        usersignUpEmail = findViewById(R.id.userSignUpEmail);
        userpassword = findViewById(R.id.userPassword);
        userconfirmPassword = findViewById(R.id.userConfirmPassword);
        signUpButton = findViewById(R.id.signUpButton);
        gotoSignIn = findViewById(R.id.goToSignIn);

        //What happens on clicking the button happens here -

        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_signin_activity = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(open_signin_activity);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usersignUpName.getText().toString();
                String userSignUpEmail = usersignUpEmail.getText().toString();
                String userPassword = userpassword.getText().toString();
                String userConfirmPassword = userconfirmPassword.getText().toString();
                if(userPassword.equals(userConfirmPassword)) {
                    UserAuthentication userAuthentication = new UserAuthentication(SignUpActivity.this);
                    userAuthentication.AuthenticateUser(userName, userSignUpEmail, userPassword);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Passwords do not match !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}