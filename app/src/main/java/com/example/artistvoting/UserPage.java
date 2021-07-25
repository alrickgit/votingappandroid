package com.example.artistvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserPage extends AppCompatActivity {
    DatabaseReference ref;
    TextView urname,uremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");

    }
}