package com.example.popularmovie.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.R;
import com.example.popularmovie.delegate.IViewCallback;
import com.example.popularmovie.model.Movie;
import com.example.popularmovie.viewholder.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {
    List<Movie> movieList = new ArrayList<>();
    IViewCallback callback = null;

    public void setData(List<Movie> movies){
        this.movieList = movies;
    }

    public void setCallback(IViewCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder tmpHolder = null;
        View tmpView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_movie, parent, false);

        tmpHolder = new MovieViewHolder(tmpView);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Movie tempMovie = movieList.get(position);
        ((MovieViewHolder) holder).setData(tempMovie);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.itemPressedCallback(movieList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
