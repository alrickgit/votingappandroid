package com.example.artistvoting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity implements  View.OnClickListener {
    ImageView ivvoteimg,ivdonateimg,ivaboutimg,ivuserimg;
    Button votebtn,donatebtn,aboutbtn,userbtn;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivaboutimg = (ImageView)findViewById(R.id.aboutimg);
        ivvoteimg = (ImageView)findViewById(R.id.voteimg);
        ivdonateimg = (ImageView)findViewById(R.id.donateimg);
        ivuserimg = (ImageView)findViewById(R.id.userimg);

        votebtn = (Button)findViewById(R.id.votebtn);
        donatebtn = (Button)findViewById(R.id.donatebtn);
        aboutbtn = (Button)findViewById(R.id.aboutbtn);
//        userbtn = (Button)findViewById(R.id.userbtn);

        votebtn.setOnClickListener(this);
        donatebtn.setOnClickListener(this);
        aboutbtn.setOnClickListener(this);
//        userbtn.setOnClickListener(this);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.votebtn:
                ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users u =snapshot.getValue(Users.class);
                        Boolean hasVoted= u.hasVoted;
                        if(!hasVoted){
                            startActivity(new Intent(HomePage.this,VotingPage.class));

                        }
                        else {
                            Toast.makeText(HomePage.this,"You have already voted",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                break;
            case R.id.donatebtn:
                startActivity((new Intent(HomePage.this,DonatePage.class)));
                break;
            case R.id.aboutbtn:
                startActivity((new Intent(HomePage.this,AboutUs.class)));
                break;
//            case R.id.userbtn:
//                startActivity((new Intent(HomePage.this,UserPage.class)));
//                break;
        }
    }


}
