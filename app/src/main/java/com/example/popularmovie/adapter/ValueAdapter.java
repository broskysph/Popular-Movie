package com.example.popularmovie.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.R;
import com.example.popularmovie.delegate.IViewCallback;
import com.example.popularmovie.model.Movie;
import com.example.popularmovie.viewholder.DetailViewHolder;
import com.example.popularmovie.viewholder.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ValueAdapter extends RecyclerView.Adapter {
    IViewCallback callback = null;
    List<String> valueList = new ArrayList<>();
    public void setData(List<String> values){
        this.valueList = values;
    }

    public void setCallback(IViewCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder tmpHolder = null;
        View tmpView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_trailler, parent, false);

        tmpHolder = new DetailViewHolder(tmpView);
        return tmpHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d("Brosky", "Putaran ke"+position);
        String tempMovie = valueList.get(position);
        ((DetailViewHolder) holder).setData(tempMovie);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(callback != null){
//                    //callback.itemPressedCallback(valueList.get(position), position);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return valueList.size();
//        return valueList.size();
    }
}
