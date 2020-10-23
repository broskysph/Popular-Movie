package com.example.popularmovie.delegate;

import com.example.popularmovie.model.Movie;

public interface IViewCallback {
    void itemPressedCallback(Movie movie, int id);
}
