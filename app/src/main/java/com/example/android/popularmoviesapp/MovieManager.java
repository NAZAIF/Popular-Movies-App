package com.example.android.popularmoviesapp;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by nazaif on 21/12/17.
 */
@Parcel
public class MovieManager {
    @SerializedName("id")
    private long id;
    @SerializedName("original_title")
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("release_date")
    private String releaseDate;

    public MovieManager() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
