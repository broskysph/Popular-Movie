package com.example.popularmovie.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    Context context;
    private MutableLiveData<List<Movie>> data = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }

    public LiveData<List<Movie>> getData() {
        return data;
    }

    public String getMovie(String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String response = "";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        List<Movie> movieList = new ArrayList<>();
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray results = json.getJSONArray("results");
                            for(int i=0;i<results.length();i++){
                                JSONObject tempMovie = results.getJSONObject(i);
                                movieList.add(new Movie(tempMovie.getString("original_title"), "http://image.tmdb.org/t/p/w185/"+tempMovie.getString("poster_path"), tempMovie.getString("vote_average"), tempMovie.getString("overview"), tempMovie.getString("release_date"), tempMovie.getString("id")));
                            }
                            data.postValue(movieList);
                        } catch (JSONException e) {
                            Log.d("Brosky",e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Brosky",error.toString());
            }
        });
        queue.add(stringRequest);
        return response;
    }

}
