package com.example.popularmovie.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.Database.ApplicationDB;
import com.example.popularmovie.Database.entity.MovieEntity;
import com.example.popularmovie.R;
import com.example.popularmovie.model.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.popularmovie.R.drawable.red_heart;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView tv_title;
    Button btnFav;
    Boolean fav=false;
    View itemView;
    int idDatabase;
    Movie movie;
    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.url_photo);
        tv_title = itemView.findViewById(R.id.movie_title);
        btnFav = itemView.findViewById(R.id.btn_fav);
        this.itemView = itemView;
        setHeart();
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav = !fav;
                setHeart();
                setFav(fav, movie);
            }
        });
    }
    private void setHeart(){
        if(fav){
            btnFav.setBackgroundResource(red_heart);
        }
        else{
            btnFav.setBackgroundResource(R.drawable.grey_heart);
        }
    }
    public void setData(Movie movie){
        tv_title.setText(movie.getTitle());
        Picasso.get().load(movie.getUrl_photo()).into(image);
        getFavMovie(movie);
        this.movie = movie;
        Log.d("Brosky", movie.getUrl_photo());
    }
    private void getFavMovie(Movie movie){
        List<MovieEntity> data = new ArrayList<>();
        data  = ApplicationDB.getInstance(itemView.getContext()).dao().selectMovie();
        Boolean temp=false;
        for(int i=0;i<data.size();i++){
            if(data.get(i).getIdMovie().equals(movie.getIdMovie())){
                Log.d("Brosky", data.get(i).getIdMovie()+"=="+movie.getTitle());
                temp = true;
                idDatabase = data.get(i).getId();
                Log.d("Brosky","Trueeeeeee");

            }
        }
        fav=temp;
        setHeart();
    }
    private void setFav(Boolean status, Movie movie){
        MovieEntity me = new MovieEntity();
        me.setIdMovie(movie.getIdMovie());
        me.setTitle(movie.getTitle());
        me.setDescription(movie.getDescription());
        me.setRating(movie.getRating());
        me.setRelease_date(movie.getRelease_date());
        me.setUrl_photo(movie.getUrl_photo());
        if(status){
            ApplicationDB.getInstance(itemView.getContext()).dao().insertMovie(me);
            Log.d("Brosky","Insert sukses");
        }
        else{
            me.setId(idDatabase);
            ApplicationDB.getInstance(itemView.getContext()).dao().deleteMovie(me);
            Log.d("Brosky", "Berusaha mendelete");
        }
    }
}
