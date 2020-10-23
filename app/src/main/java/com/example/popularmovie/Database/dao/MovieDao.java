package com.example.popularmovie.Database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.popularmovie.Database.entity.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insertMovie(MovieEntity movieEntity);

    @Query("select * from movie")
    List<MovieEntity> selectMovie();

    @Update
    void updateUsers(MovieEntity movieEntity);

    @Delete
    void deleteMovie(MovieEntity movie);
}
