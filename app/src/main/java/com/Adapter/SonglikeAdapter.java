package com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Activity.Playermusic;
import com.Model.Song;
//import com.example.silverp.musicdemo.Activity.PlaymusicActivity;
//import com.example.silverp.musicdemo.Model.Song;
//import com.example.silverp.musicdemo.R;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SonglikeAdapter extends RecyclerView.Adapter<SonglikeAdapter.ViewHoder> {
    Context context;
    ArrayList<Song> songs;

    public SonglikeAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.dong_songlike,viewGroup,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        Song song= songs.get(i);
        viewHoder.txtnamesong.setText(song.getNamesong());
        viewHoder.txtsinger.setText(song.getSinger());
        Picasso.with(context).load(song.getImagesong()).into(viewHoder.imgsong);

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtnamesong,txtsinger;
        ImageView imgsong;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtnamesong=itemView.findViewById(R.id.dong_songlike_namesong);
            txtsinger=itemView.findViewById(R.id.dong_songlike_singer);
            imgsong=itemView.findViewById(R.id.dong_songlike_imagesong);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, Playermusic.class);
                    intent.putExtra("Song",songs.get(getPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
