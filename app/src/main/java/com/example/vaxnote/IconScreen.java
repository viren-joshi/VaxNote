package com.example.vaxnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
            Intent open_sign_in = new Intent(IconScreen.this,SignInActivity.class);
            startActivity(open_sign_in);
            finish();
        },splashscreen);

    }
}