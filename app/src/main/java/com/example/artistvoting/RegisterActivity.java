package com.example.artistvoting;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText etusername,etemail,etcpassword;
    EditText etpassword;
    TextView tvoldmem;
    Button btnregister;
    ImageView ivlogo;
    ProgressBar pg;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        etusername =(EditText)findViewById(R.id.username);
        etpassword =(EditText)findViewById(R.id.password);
        etcpassword =(EditText)findViewById(R.id.cpassword);
        etemail =(EditText)findViewById(R.id.email);
        tvoldmem =(TextView) findViewById(R.id.oldmem);
        btnregister = (Button)findViewById(R.id.registerbtn);
        ivlogo = (ImageView)findViewById(R.id.logo);
        pg = (ProgressBar) findViewById(R.id.progress);
        videoView = (VideoView)findViewById(R.id.videoView);
        tvoldmem.setOnClickListener(this);
        btnregister.setOnClickListener(this);
        ivlogo.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video2);
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
            case R.id.oldmem:
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                break;
//            case R.id.logo:
//                startActivity(new Intent(RegisterActivity.this,HomePage.class));
//                break;
            case R.id.registerbtn:
                registeruser();
                break;

        }
    }

    private void registeruser() {
        String useremail = etemail.getText().toString().trim();
        String name = etusername.getText().toString().trim();
        String pass = etpassword.getText().toString().trim();
        String cpass = etcpassword.getText().toString().trim();

        if(name.isEmpty()){
            etusername.setError("Name is required!");
            etusername.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(useremail)){
            etemail.setError("Email is required!");
            etemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()){
            etemail.setError("Please enter a valid email!");
            etemail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            etpassword.setError("Password is required!");
            etpassword.requestFocus();
            return;
        }

        if(pass.length()<6){
            etpassword.setError("Min password length should be six characters");
            etpassword.requestFocus();
            return;
        }

        if(cpass.isEmpty()){
            etcpassword.setError("Have to Confirm Password!");
            etcpassword.requestFocus();
            return;
        }

        if(!pass.equals(cpass)){
            etcpassword.setError("Password's dont match");
            etcpassword.requestFocus();
            return;
        }
        pg.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(useremail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Boolean hasVoted=false;
                    Users user= new Users(name,useremail,hasVoted);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pg.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Sign up successful.",Toast.LENGTH_LONG).show();
//                                FirebaseAuth.getInstance().signOut();
                                Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                pg.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Failed to registered!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else {
                    pg.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Failed to registered!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
