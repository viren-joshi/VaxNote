package com.example.vaxnote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaxnote.R;
import com.example.vaxnote.classes.UserAuthentication;

public class SignUpActivity extends AppCompatActivity {
    EditText usersignUpName, usersignUpEmail, userpassword, userconfirmPassword;
    Button signUpButton;
    TextView gotoSignIn;
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

        gotoSignIn.setOnClickListener(v -> {
            Intent open_signin_activity = new Intent(SignUpActivity.this,SignInActivity.class);
            startActivity(open_signin_activity);
        });

        signUpButton.setOnClickListener(v -> {
            String userName = usersignUpName.getText().toString();
            String userSignUpEmail = usersignUpEmail.getText().toString();
            String userPassword = userpassword.getText().toString();
            String userConfirmPassword = userconfirmPassword.getText().toString();
            if(userPassword.equals(userConfirmPassword)) {
                 UserAuthentication userAuthentication = new UserAuthentication(SignUpActivity.this);
                 userAuthentication.SignUp(userName, userSignUpEmail, userPassword);
            }
            else{
                Toast.makeText(SignUpActivity.this, "Passwords do not match !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
