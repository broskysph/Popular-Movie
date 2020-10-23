package com.example.popularmovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.popularmovie.Database.ApplicationDB;
import com.example.popularmovie.Database.entity.MovieEntity;
import com.example.popularmovie.R;
import com.example.popularmovie.ViewModel.MainViewModel;
import com.example.popularmovie.adapter.MovieAdapter;
import com.example.popularmovie.delegate.IViewCallback;
import com.example.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IViewCallback {

    RecyclerView rv;
    MovieAdapter movieAdapter = new MovieAdapter();
    ProgressBar pb;
    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        rv = findViewById(R.id.recyclerview_movie);
        pb = findViewById(R.id.custom_spinner);
        pb.setVisibility(View.GONE);
        rv.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter.setCallback(this);
        rv.setAdapter(movieAdapter);
        //rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        mainViewModel.getMovie("http://api.themoviedb.org/3/movie/popular?api_key=363a304bf3803692ae784f270971be88");
        mainViewModel.getData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setData(movies);
                movieAdapter.notifyDataSetChanged();
            }
        });
        setTitle("Popular Movie");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.top) {
            // do something here
            mainViewModel.getMovie("http://api.themoviedb.org/3/movie/top_rated?api_key=363a304bf3803692ae784f270971be88");
            setTitle("Top Rated Movie");
            Log.d("Brosky", "Top Clicked");
        }
        else if (id == R.id.popular){
            setTitle("Popular Movie");
            mainViewModel.getMovie("http://api.themoviedb.org/3/movie/popular?api_key=363a304bf3803692ae784f270971be88");

            Log.d("Brosky", "Popular Clicked");
        }
        else if(id == R.id.favorite){
            setTitle("Favorites");
            //getMovie("http://api.themoviedb.org/3/movie/popular?api_key=363a304bf3803692ae784f270971be88");
            getFromDB();
            Log.d("Brosky", "Popular Clicked");
        }
        return super.onOptionsItemSelected(item);
    }

    public void getFromDB(){
        List<MovieEntity> data = new ArrayList<>();
        data = ApplicationDB.getInstance(getApplicationContext()).dao().selectMovie();
        List<Movie> movieList = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            movieList.add(new Movie(data.get(i).getTitle(), data.get(i).getUrl_photo(), data.get(i).getRating(), data.get(i).getDescription(), data.get(i).getRelease_date(), data.get(i).getIdMovie()));
        }
        movieAdapter.setData(movieList);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieAdapter.notifyDataSetChanged();
    }

//    public String getMovie(String url) {
//        pb.setVisibility(View.VISIBLE);
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String response = "";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        List<Movie> movieList = new ArrayList<>();
//                        try {
//                            JSONObject json = new JSONObject(response);
//                            JSONArray results = json.getJSONArray("results");
//                            for(int i=0;i<results.length();i++){
//                                JSONObject tempMovie = results.getJSONObject(i);
//                                movieList.add(new Movie(tempMovie.getString("original_title"), "http://image.tmdb.org/t/p/w185/"+tempMovie.getString("poster_path"), tempMovie.getString("vote_average"), tempMovie.getString("overview"), tempMovie.getString("release_date"), tempMovie.getString("id")));
//                            }
//                            movieAdapter.setData(movieList);
//                            movieAdapter.notifyDataSetChanged();
//                            pb.setVisibility(View.GONE);
//                        } catch (JSONException e) {
//                            Log.d("Brosky",e.toString());
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Brosky",error.toString());
//            }
//        });
//        queue.add(stringRequest);
//        return response;
//    }

    @Override
    public void itemPressedCallback(Movie movie, int id) {
        Intent myIntent = new Intent(this, MainActivity2.class);
        JSONObject temp = new JSONObject();
        try {
            temp.put("title", movie.getTitle());
            temp.put("url_photo", movie.getUrl_photo());
            temp.put("rating", movie.getRating());
            temp.put("description", movie.getDescription());
            temp.put("release_date", movie.getRelease_date());
            temp.put("id", movie.getIdMovie());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myIntent.putExtra("movie", temp.toString());
        startActivity(myIntent);
        Log.d("Brosky", "Ready to go another screen");
    }

}