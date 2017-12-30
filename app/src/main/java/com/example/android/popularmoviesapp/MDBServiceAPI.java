package com.example.android.popularmoviesapp;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nazaif on 21/12/17.
 */

public class MDBServiceAPI {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POSTER_URL = "http://image.tmdb.org/t/p/w185";
    public static final String BACKDROP_URL = "http://image.tmdb.org/t/p/w780";

    public static final String SORTBY_TOP_RATED = "top_rated";
    public static final String SORTBY_POPULAR = "popular";
    public static final String SORTBY_NOW_PLAYING = "now_playing";
    public static final String SORTBY_DEFAULT = SORTBY_POPULAR;

    /**
     * Add the API KEY in below line
     */

    public static final String API_KEY = "";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit retrofit;


    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging).addInterceptor(new AuthInterceptor());


        retrofit =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        return retrofit.create(serviceClass);
    }

    private static class AuthInterceptor implements Interceptor {



        @Override
        public Response intercept(Chain chain) throws IOException {
                HttpUrl url = chain.request().url()
                        .newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build();
                Request request = chain.request().newBuilder().url(url).build();
            return chain.proceed(request);
        }
    }
}
