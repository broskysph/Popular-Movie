package com.example.popularmovie.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmovie.Database.dao.MovieDao;
import com.example.popularmovie.Database.entity.MovieEntity;

@Database(entities = {MovieEntity.class}, version=1)
public abstract class ApplicationDB extends RoomDatabase {
    public abstract MovieDao dao();
    private static ApplicationDB db;

    public static ApplicationDB getInstance(Context context){
        if(db == null){
            db = Room.databaseBuilder(context, ApplicationDB.class, "TEST").allowMainThreadQueries().build();;
        }
        return db;
    }
}
