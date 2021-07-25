package com.example.artistvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class AboutUs extends AppCompatActivity {
    VideoView videoView;
    ImageView logo;
    TextView abttxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        videoView = (VideoView)findViewById(R.id.videoView);
        logo = (ImageView)findViewById(R.id.logo);
        abttxt = (TextView)findViewById(R.id.abttxt);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video3);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoView.start();
    }
}