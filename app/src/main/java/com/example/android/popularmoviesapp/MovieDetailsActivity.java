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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends MainActivity {

    CardView c2;
    ProgressBar progressBar;
    ImageView iv_backdrop, iv_poster;
    TextView tv_rating, tv_release, tv_overview, tv_title;
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

        c2 = (CardView) findViewById(R.id.overview_card);
        progressBar = (ProgressBar) findViewById(R.id.pgbar);
        tv_title = (TextView) findViewById(R.id.title_text);
        iv_backdrop = (ImageView) findViewById(R.id.backdrop_image);
        iv_poster = (ImageView) findViewById(R.id.poster_image);
        tv_overview = (TextView) findViewById(R.id.overview_text);
        tv_release = (TextView) findViewById(R.id.release_text);
        tv_rating = (TextView) findViewById(R.id.rating_text);

        long movieId = getIntent().getExtras().getLong(MainActivity.EXTRA_ID);

        getMovieDetails(movieId);
    }

    public void back(View view) {
        finish();
    }

    public void getMovieDetails(long id) {
        progressBar.setVisibility(View.VISIBLE);
        Call<MovieManager> call = mAPI.getMovieInfo(id);
        call.enqueue(new Callback<MovieManager>() {
            @Override
            public void onResponse(Call<MovieManager> call, Response<MovieManager> response) {
                c2.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                movieManager = response.body();
                tv_rating.setText(movieManager.getRating());
                tv_rating.setTypeface(null, Typeface.BOLD);
                tv_release.setText(movieManager.getReleaseDate());
                tv_overview.setText(movieManager.getOverview());
                tv_title.setText(movieManager.getTitle());

                Picasso.with(MovieDetailsActivity.this)
                        .load(MDBServiceAPI.POSTER_URL + movieManager.getPoster())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_img)
                        .into(iv_poster);

                Picasso.with(MovieDetailsActivity.this)
                        .load(MDBServiceAPI.BACKDROP_URL + movieManager.getBackdrop())
                        .placeholder(R.drawable.placeholder_land)
                        .error(R.drawable.error_img)
                        .into(iv_backdrop);
            }

            @Override
            public void onFailure(Call<MovieManager> call, Throwable t) {
                Toast.makeText(MovieDetailsActivity.this, "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
