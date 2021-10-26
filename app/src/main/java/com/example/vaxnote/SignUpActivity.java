package com.example.vaxnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText userSignUpName, userSignUpEmail, userPassword, userConfirmPassword;
    Button signUpButton;
    Intent open_main_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the views -
        setContentView(R.layout.activity_sign_up);
        userSignUpName = findViewById(R.id.userSignUpName);
        userSignUpEmail = findViewById(R.id.userSignUpEmail);
        userPassword = findViewById(R.id.userPassword);
        userConfirmPassword = findViewById(R.id.userConfirmPassword);
        signUpButton = findViewById(R.id.signUpButton);

        //What happens on clicking the button happens here -
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userSign_UpName = userSignUpName.getText().toString();
                String userSign_UpEmail = userSignUpEmail.getText().toString();
                String user_Password = userPassword.getText().toString();
                String useConfirm_Password = userConfirmPassword.getText().toString();
                Log.d("User_Login",   userSign_UpName +" " + userSign_UpEmail + " " + user_Password + " " + userConfirmPassword);
                Toast.makeText(SignUpActivity.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                open_main_activity = new Intent(SignUpActivity.this, MainActivity.class);
            }
        });
    }
}