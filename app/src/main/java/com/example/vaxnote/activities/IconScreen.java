package com.example.vaxnote.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.vaxnote.R;
import com.example.vaxnote.classes.SharedPrefManager;

public class IconScreen extends AppCompatActivity {

    private static final int splashscreen=5000;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_icon_screen);


        image = findViewById(R.id.imageView);


        new Handler().postDelayed(() -> {
            if(SharedPrefManager.getInstance(this).isSignedIn()){
                startActivity(new Intent(this,MainMenuActivity.class));
                finish();
            }
            else{
                startActivity(new Intent(this,first_page.class));
            }
            finish();
        },splashscreen);
    }
}