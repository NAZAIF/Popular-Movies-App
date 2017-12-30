package com.example.android.popularmoviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nazaif on 20/12/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static int holderCount;
    ClickListener clickListener;
    Context context;
    List<MovieManager> movieManager;

    public MovieAdapter(Context c, List<MovieManager> movieManagerList, ClickListener listener) {
        this.context = c;
        this.movieManager = movieManagerList;
        this.clickListener = listener;
        holderCount = 0;
    }

    public interface ClickListener {
        void onListClick(int index);
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        MovieAdapterViewHolder holder = new MovieAdapterViewHolder(v);
        int holderColor = ColorUtils.getViewHolderBackgroundColorFromInstance(context,holderCount);
        holder.title_textView.setBackgroundColor(holderColor);
        holderCount++;
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        MovieManager movie = movieManager.get(position);
        Picasso.with(context).load(MDBServiceAPI.POSTER_URL + movie.getPoster())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_img)
                .into(holder.title_imageView);
        holder.title_textView.setText(movie.getTitle());
        holder.itemView.setTag(movie);
    }

    @Override
    public int getItemCount() {
        return movieManager.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final TextView title_textView;
        public final ImageView title_imageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            title_textView = (TextView) itemView.findViewById(R.id.cardTitleText);
            title_imageView = (ImageView) itemView.findViewById(R.id.cardImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onListClick(getAdapterPosition());
        }
    }
}
