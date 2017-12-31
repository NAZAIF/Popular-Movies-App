package com.example.android.popularmoviesapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;


public class MovieDetailsActivity extends MainActivity {

    CardView cardView;
    ProgressBar progressBar;
    ImageView ivBackdrop, ivPoster;
    TextView tvRating, tvRelease, tvOverview, tvTitle;
    private MovieManager movieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        cardView = (CardView) findViewById(R.id.overview_card);
        progressBar = (ProgressBar) findViewById(R.id.pgBar);
        tvTitle = (TextView) findViewById(R.id.title_text);
        ivBackdrop = (ImageView) findViewById(R.id.backdrop_image);
        ivPoster = (ImageView) findViewById(R.id.poster_image);
        tvOverview = (TextView) findViewById(R.id.overview_text);
        tvRelease = (TextView) findViewById(R.id.release_text);
        tvRating = (TextView) findViewById(R.id.rating_text);

        movieManager = (MovieManager) Parcels.unwrap(getIntent().getParcelableExtra("movieDetails"));
        getDetails(movieManager);

    }

    public void back(View view) {
        finish();
    }

    private void getDetails(MovieManager movie) {
        cardView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        tvRating.setText(movieManager.getRating());
        tvRating.setTypeface(null, Typeface.BOLD);
        tvRelease.setText(movieManager.getReleaseDate());
        tvOverview.setText(movieManager.getOverview());
        tvTitle.setText(movieManager.getTitle());

        Picasso.with(MovieDetailsActivity.this)
                .load(MDBServiceAPI.POSTER_URL + movieManager.getPoster())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_img)
                .into(ivPoster);

        Picasso.with(MovieDetailsActivity.this)
                .load(MDBServiceAPI.BACKDROP_URL + movieManager.getBackdrop())
                .placeholder(R.drawable.placeholder_land)
                .error(R.drawable.error_img)
                .into(ivBackdrop);
    }

}
