package com.example.artistvoting;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>{

    private List<Artist> artistList;
    private Artistslistener artistslistener;

    public ArtistsAdapter(List<Artist> artistList, Artistslistener artistslistener) {
        this.artistList = artistList;
        this.artistslistener = artistslistener;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_artists,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {

        holder.bindArtist(artistList.get(position));
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public List<Artist> getSelectedArtists() {
        List<Artist> selectedArtists = new ArrayList<>();
        for (Artist artist : artistList) {
            if (artist.isSelected){
                selectedArtists.add(artist);
            }
        }
        return selectedArtists;
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layoutArtist;
        View viewBackground;
        RoundedImageView imageArtist;
        TextView textName, textCreatedby, textStory;
        RatingBar ratingBar;
        ImageView imageSelected;


        ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutArtist = itemView.findViewById(R.id.layoutartists);
            viewBackground = itemView.findViewById(R.id.viewBackground);
            imageArtist = itemView.findViewById(R.id.imageArtist);
            textName = itemView.findViewById(R.id.textName);
            textCreatedby = itemView.findViewById(R.id.textCreatedby);
            textStory = itemView.findViewById(R.id.textStory);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            imageSelected = itemView.findViewById(R.id.imageSelected);

        }

        void bindArtist(final Artist artist){
            imageArtist.setImageResource(artist.image);
            textName.setText(artist.name);
            textCreatedby.setText(artist.createdBy);
            textStory.setText(artist.story);
            ratingBar.setRating(artist.rating);
            if(artist.isSelected){
                viewBackground.setBackgroundResource(R.drawable.artist_selected_background);
                imageSelected.setVisibility(View.VISIBLE);
            }else{
                viewBackground.setBackgroundResource(R.drawable.artist_bg);
                imageSelected.setVisibility(View.GONE);
            }

            layoutArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (artist.isSelected) {
                        viewBackground.setBackgroundResource(R.drawable.artist_bg);
                        imageSelected.setVisibility(View.GONE);
                        artist.isSelected = false;
                        if(getSelectedArtists().size()== 0) {
                            artistslistener.onArtistAction(false);
                        }
                    }
                    else{
                        if(getSelectedArtists().size()<1){
                            viewBackground.setBackgroundResource(R.drawable.artist_selected_background);
                            imageSelected.setVisibility(View.VISIBLE);
                            artist.isSelected = true;
                            artistslistener.onArtistAction(true);
                        }
                    }

                }
            });


        }
    }
}
