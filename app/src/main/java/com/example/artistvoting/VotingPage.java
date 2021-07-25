package com.example.artistvoting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VotingPage extends AppCompatActivity implements Artistslistener{

    private Button buttonVote;
    DatabaseReference ref,reference;
    Votes v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_page);

        RecyclerView artist_list = findViewById(R.id.artist_list);
        buttonVote = findViewById(R.id.buttonVote);
        ref = FirebaseDatabase.getInstance().getReference().child("Votes");
        v = new Votes();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        List<Artist> artists = new ArrayList<>();

        Artist alkyn = new Artist();
        alkyn.image = R.drawable.alkyn;
        alkyn.name = "ALKYN";
        alkyn.rating = 3.5f;
        alkyn.createdBy = "Future, Vapor Twitch, Trap";
        alkyn.story = "One of the biggest up and coming producers in the scene";
        artists.add(alkyn);

        Artist baynk = new Artist();
        baynk.image = R.drawable.baynk;
        baynk.name = "BAYNK";
        baynk.rating = 4f;
        baynk.createdBy = "Electronic, Chill, Experimental House";
        baynk.story = "Known throughout the world for his chill and dance-y music";
        artists.add(baynk);

        Artist bleuclair = new Artist();
        bleuclair.image = R.drawable.bleuclair;
        bleuclair.name = "BLEU CLAIR";
        bleuclair.rating = 4f;
        bleuclair.createdBy = "Future Tech-House, Techno";
        bleuclair.story = "Changed the whole scene with his new future tech-house sound";
        artists.add(bleuclair);

        Artist curbi = new Artist();
        curbi.image = R.drawable.curbi;
        curbi.name = "CURBI";
        curbi.rating = 4.8f;
        curbi.createdBy = "Tech-House, Bass House";
        curbi.story = "Futuristic Bass house god dropping heat like he was born to do so";
        artists.add(curbi);

        Artist sanholo = new Artist();
        sanholo.image = R.drawable.sanholo;
        sanholo.name = "SAN HOLO";
        sanholo.rating = 5f;
        sanholo.createdBy = "Future Bass, Guitar Trap, Electronic";
        sanholo.story = "One of the biggest acts of electronic music";
        artists.add(sanholo);

        Artist flume = new Artist();
        flume.image = R.drawable.flume;
        flume.name = "FLUME";
        flume.rating = 5f;
        flume.createdBy = "Atmospheric Glitch infused Dance Music, Future";
        flume.story = "Grammy winner revolutionizing music since he begun";
        artists.add(flume);



        final ArtistsAdapter artistsAdapter = new ArtistsAdapter(artists, this);
        artist_list.setAdapter(artistsAdapter);

        buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String selectedArtist = artistsAdapter.getSelectedArtists().toString();
//                Toast.makeText(VotingPage.this,selectedArtist, Toast.LENGTH_LONG).show();
                List<Artist> selectedArtists = artistsAdapter.getSelectedArtists();

                StringBuilder artistNames = new StringBuilder();
                for (int i = 0; i<selectedArtists.size(); i++) {
                    if(i == 0) {
                        artistNames.append(selectedArtists.get(i).name);
                    }
                    else{
                        artistNames.append("\n").append(selectedArtists.get(i).name);

                    }
                }
                String selectedname = artistNames.toString().trim().toLowerCase();
                String selectname = selectedname.replaceAll("\\s", "");
                ref.child(selectname).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Votes v=snapshot.getValue(Votes.class);

                        if(v !=null) {
                            int nvote=v.no_ofvotes+1;
                            Votes n = new Votes(nvote);
                            ref.child(selectname).setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(VotingPage.this, "Thankyou for Voting!", Toast.LENGTH_SHORT).show();
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Users x = snapshot.getValue(Users.class);
                                                x.hasVoted=true;
                                                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(x);
                                                startActivity(new Intent(VotingPage.this,HomePage.class));
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

    @Override
    public void onArtistAction(Boolean isSelected) {

        if (isSelected){
            buttonVote.setVisibility(View.VISIBLE);
        }else{
            buttonVote.setVisibility(View.GONE);
        }


    }
}