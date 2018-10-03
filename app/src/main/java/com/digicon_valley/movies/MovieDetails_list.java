package com.digicon_valley.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class MovieDetails_list extends AppCompatActivity {
    private ImageView image;
    private TextView titles, date, rating, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_list);

        image = findViewById(R.id.poster);
        titles = findViewById(R.id.titles);
        date = findViewById(R.id.date);
        rating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);

        MovieDetails details= (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");

        if(details != null) {


            Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + details.getPoster_path()).into(image);
            titles.setText(details.getOriginal_title());
            date.setText(details.getRelease_date());
            rating.setText(Double.toString(details.getVote_average()));
            overview.setText(details.getOverview());
        }

    }
}