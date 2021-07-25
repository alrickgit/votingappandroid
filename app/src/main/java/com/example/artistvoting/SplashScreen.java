package com.example.artistvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo = (ImageView)findViewById(R.id.logosp);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(i);
                        finish(); //no returning back
                    }
                },
                1000
        ); //closes the post delayed

    }
}