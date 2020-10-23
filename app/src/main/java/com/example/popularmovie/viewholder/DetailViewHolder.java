package com.example.popularmovie.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.R;
import com.example.popularmovie.model.Movie;
import com.squareup.picasso.Picasso;

import static androidx.core.content.ContextCompat.startActivity;

public class DetailViewHolder extends RecyclerView.ViewHolder {
    TextView tv_detail;
    String link="";
    Context context;
    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_detail = itemView.findViewById(R.id.movie_value);
        context = itemView.getContext();
    }
    private void openYoutube(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        intent.setPackage("com.google.android.youtube");
        context.startActivity(intent);
    }
    ClickableSpan clickableSpan1 = new ClickableSpan() {
        @Override
        public void onClick(@NonNull View widget) {
            openYoutube();
        }
    };
    public void setData(String text){
        char lastChar = text.charAt(text.length() - 1);
        if(lastChar == '~'){
            text = text.substring(0, text.length() - 1);
            Log.d("Brosky", "Sebelum split");
            String[] temp = text.split("\\?");
            Log.d("Brosky", "Panjang temp: "+temp.length);
            text="";
            for(int i=0;i<temp.length - 1;i++){
                if(i>0) text = text + "?";
                text = text + temp[i];
            }

            link = "http://www.youtube.com/watch?v="+temp[temp.length - 1];
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(clickableSpan1,0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_detail.setText(spannableString);
            tv_detail.setMovementMethod(LinkMovementMethod.getInstance());

        }
        else{
            tv_detail.setText(text);
        }
    }
}
