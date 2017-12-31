package com.example.android.popularmoviesapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nazaif on 21/12/17.
 */


public interface MovieAPIinterface {
    @GET("movie/{sortby}")
    Call<MovieResults> getPopular(@Path("sortby") String sortBy);

    @GET("movie/{movie_id}")
    Call<MovieManager> getMovieInfo(@Path("movie_id") long movieId);


}
