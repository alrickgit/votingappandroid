package com.example.artistvoting;
import android.app.StatusBarManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email;
    private FirebaseAuth mAuth;
    EditText password;
    TextView newmem;
    Button loginbtn;
    ImageView logo;
    ProgressBar pg;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);
        email =(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        newmem =(TextView) findViewById(R.id.newmem);
        loginbtn = (Button)findViewById(R.id.loginbtn);
        logo = (ImageView)findViewById(R.id.logo);
        pg = (ProgressBar) findViewById(R.id.progress);
        videoView = (VideoView)findViewById(R.id.videoView);

        newmem.setOnClickListener(this);
        logo.setOnClickListener(this);
        loginbtn.setOnClickListener(this);



        mAuth = FirebaseAuth.getInstance();
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video1);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
            }
        });
        videoView.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newmem:
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                break;
//            case R.id.logo:
//                startActivity(new Intent(MainActivity.this,HomePage.class));
//                break;
            case R.id.loginbtn:
                userlogin();
                break;
        }
    }
    private void userlogin() {
        String semail=email.getText().toString().trim();
        Log.d("hello","123");
        String spass=password.getText().toString().trim();

        if(semail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            email.setError("Please enter a valid Email");
            email.requestFocus();
            return;
        }

        if(spass.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        pg.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(semail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    pg.setVisibility(View.GONE);
                    Intent i=new Intent(MainActivity.this,HomePage.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Check your credentials!",Toast.LENGTH_LONG).show();
                    pg.setVisibility(View.GONE);
                }

            }
        });

    }

}
