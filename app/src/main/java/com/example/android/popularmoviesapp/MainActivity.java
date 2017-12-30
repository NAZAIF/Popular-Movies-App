package com.example.android.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ClickListener {

    ProgressBar pgbar;
    TextView titleText;
    Toast toast;
    RecyclerView recyclerView;
    MovieAPIinterface mAPI;
    private List<MovieManager> movieList;
    MovieAdapter movieAdapter;
    public static final String EXTRA_ID = "movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAPI = MDBServiceAPI.createService(MovieAPIinterface.class);
        titleText = (TextView) findViewById(R.id.mainTitle);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        pgbar = (ProgressBar) findViewById(R.id.pgbar);
        recyclerView = (RecyclerView) findViewById(R.id.rcv);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        toast = Toast.makeText(this, R.string.error_internet, Toast.LENGTH_SHORT);

        if (isOnline()) {
            getMovieData(MDBServiceAPI.SORTBY_DEFAULT);
            pgbar.setVisibility(View.VISIBLE);
        } else
            toast.show();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void setAdapter(RecyclerView rv) {
        movieAdapter = new MovieAdapter(this, movieList, this);
        rv.setAdapter(movieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();
        if (isOnline()) {
            pgbar.setVisibility(View.VISIBLE);
            switch (selected) {
                case R.id.action_now_playing: {
                    titleText.setText(R.string.now_playing_str);
                    getMovieData(MDBServiceAPI.SORTBY_NOW_PLAYING);
                    return true;
                }

                case R.id.action_most_popular: {
                    titleText.setText(R.string.most_popular_str);
                    getMovieData(MDBServiceAPI.SORTBY_POPULAR);
                    return true;
                }

                case R.id.action_top_rated: {
                    titleText.setText(R.string.top_rated_str);
                    getMovieData(MDBServiceAPI.SORTBY_TOP_RATED);
                    return true;
                }
            }

        } else
            toast.show();
        return super.onOptionsItemSelected(item);
    }


    public void getMovieData(String sortBy) {
        Call<MovieResults> call = mAPI.getPopular(sortBy);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if (response.isSuccessful()) {
                    pgbar.setVisibility(View.INVISIBLE);
                    movieList = response.body().getMovieResults();
                    if (movieList.size() != 0) {
                        setAdapter(recyclerView);
                    } else
                        Toast.makeText(MainActivity.this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onListClick(int index) {
        MovieManager movieClicked = movieList.get(index);
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_ID, movieClicked.getId());
        startActivity(intent);
    }
}
