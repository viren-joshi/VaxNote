package com.example.vaxnote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SignInActivity extends AppCompatActivity {
    EditText userloginEmail,userloginPassword;
    TextView goToSignUp;
    Button SignIn;
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
//                 UserAuthentication userAuthentication = new UserAuthentication(SignInActivity.this);
//                 userAuthentication.AuthenticateUser(userLoginEmail,userLoginPassword);
                Intent open_main_activity = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(open_main_activity);
            }
        });
    }
}
