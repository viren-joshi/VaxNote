package com.example.vaxnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class IconScreen extends AppCompatActivity {

    private static final int splashscreen=5000;

    ImageView image;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_icon_screen);


        image = findViewById(R.id.imageView);
        name = findViewById(R.id.textView);


        new Handler().postDelayed(() -> {
            if(SharedPrefManager.getInstance(this).isSignedIn()){
                startActivity(new Intent(this,MainActivity.class));
            }
            else{
                startActivity(new Intent(this,SignInActivity.class));
            }
            finish();
        },splashscreen);

    }
}