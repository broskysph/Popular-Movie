package com.example.popularmovie.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.popularmovie.Database.ApplicationDB;
import com.example.popularmovie.Database.entity.MovieEntity;
import com.example.popularmovie.R;
import com.example.popularmovie.adapter.ValueAdapter;
import com.example.popularmovie.model.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.popularmovie.R.drawable.red_heart;

public class MainActivity2 extends AppCompatActivity {
    TextView tv_title, tv_release_date, tv_rating, tv_description;
    ImageView iv_url_photo;
    RecyclerView rv_trailer, rv_review;
    ValueAdapter valueAdapterTrailer = new ValueAdapter();
    ValueAdapter valueAdapterReview = new ValueAdapter();
    Button btnFav;
    Boolean fav = false;
    String id="";
    JSONObject movie;
    int idDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rv_trailer = findViewById(R.id.recyclerview_trailer);
        rv_review = findViewById(R.id.recyclerview_review);


        rv_trailer.setAdapter(valueAdapterTrailer);
        rv_review.setAdapter(valueAdapterReview);

        rv_review.setLayoutManager(new LinearLayoutManager(this));
        rv_trailer.setLayoutManager(new LinearLayoutManager(this));

        tv_title = findViewById(R.id.tv_title);
        tv_release_date = findViewById(R.id.tv_release_date);
        tv_rating = findViewById(R.id.tv_rating);
        tv_description = findViewById(R.id.tv_description);
        iv_url_photo = findViewById(R.id.iv_url_photo);
        btnFav = findViewById(R.id.btn_fav);

        setHeart();

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav = !fav;
                setHeart();
                setFav(fav);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent myIntent = getIntent();
        String temp = myIntent.getStringExtra("movie");
        try {
            movie = new JSONObject(temp);
            tv_title.setText(movie.getString("title"));
            tv_description.setText(movie.getString("description"));
            tv_rating.setText((movie.getString("rating")));
            tv_release_date.setText(movie.getString("release_date"));
            id=movie.getString("id");
            Toast.makeText(getApplicationContext(),"id = " + id, Toast.LENGTH_LONG).show();
            Picasso.get().load(movie.getString("url_photo")).into(iv_url_photo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getReview(id);
        getTrailer(id);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Brosky", "On resume screen 2");
        try {
            getFavMovie(new Movie(movie.getString("title"), movie.getString("url_photo"), movie.getString("rating"), movie.getString("description"), movie.getString("release_date"), movie.getString("id")));
            Log.d("Brosky", "Masuk try");
        } catch (JSONException e) {
            Log.d("Brosky", "Catch json");
            Log.d("Brosky", ""+e);
            e.printStackTrace();
        }
    }

    private void getFavMovie(Movie movie){
        Log.d("Brosky", "GetFavMovieStart");
        List<MovieEntity> data;
        data = ApplicationDB.getInstance(getApplicationContext()).dao().selectMovie();
        for(int i=0;i<data.size();i++){
            if(data.get(i).getIdMovie().equals(movie.getIdMovie())){
                fav = true;
                idDatabase = data.get(i).getId();
                Log.d("Brosky","GetFavMovieTrueeeeeee");
                setHeart();
            }
            else{
                Log.d("Brosky","GetFavMovieFalseee");
            }
        }
    }
    private void setFav(Boolean status){
        MovieEntity me = new MovieEntity();
        try {
            me.setIdMovie(movie.getString("id"));
            me.setTitle(movie.getString("title"));
            me.setDescription(movie.getString("description"));
            me.setRating((movie.getString("rating")));
            me.setRelease_date(movie.getString("release_date"));
            me.setUrl_photo(movie.getString("url_photo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(status){
            ApplicationDB.getInstance(getApplicationContext()).dao().insertMovie(me);
            Log.d("Brosky","Insert sukses");
        }
        else{
            me.setId(idDatabase);
            ApplicationDB.getInstance(getApplicationContext()).dao().deleteMovie(me);
            Log.d("Brosky", "Berusaha mendelete");
        }
    }

    private void setHeart(){
        if(fav){
            btnFav.setBackgroundResource(red_heart);
        }
        else{
            btnFav.setBackgroundResource(R.drawable.grey_heart);
        }
    }
    public String getReview(String id) {
        String url = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=363a304bf3803692ae784f270971be88";
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = "";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        List<String> detail = new ArrayList<>();
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.d("Brosky", json.toString());
                            JSONArray results = json.getJSONArray("results");
                            for(int i=0;i<results.length();i++){
                                JSONObject tempMovie = results.getJSONObject(i);
                                detail.add(tempMovie.getString("content"));
                            }
                            valueAdapterReview.setData(detail);
                            valueAdapterReview.notifyDataSetChanged();
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
    public String getTrailer(String id) {
        String url = "http://api.themoviedb.org/3/movie/"+ id +"/videos?api_key=363a304bf3803692ae784f270971be88";
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = "";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        List<String> detail = new ArrayList<>();
                        try {
                            JSONObject json = new JSONObject(response);
                            Log.d("Brosky", json.toString());
                            JSONArray results = json.getJSONArray("results");
                            for(int i=0;i<results.length();i++){
                                JSONObject tempMovie = results.getJSONObject(i);
                                detail.add(tempMovie.getString("name") + "?" + tempMovie.getString("key") + "~");
                            }
                            valueAdapterTrailer.setData(detail);
                            valueAdapterTrailer.notifyDataSetChanged();
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