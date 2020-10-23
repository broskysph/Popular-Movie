package com.example.popularmovie.Database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.popularmovie.model.Movie;

@Entity(tableName = "movie")
public class MovieEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    String url_photo;
    String title;
    String rating;
    String description;
    String release_date;
    String idMovie;

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
