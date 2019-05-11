package com.Adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Activity.Playermusic;
import com.Model.Song;
//import com.example.silverp.musicdemo.Activity.PlaymusicActivity;
//import com.example.silverp.musicdemo.Model.Song;
//import com.example.silverp.musicdemo.R;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SonglikeAdapter extends RecyclerView.Adapter<SonglikeAdapter.ViewHoder> {
    Context context;
    ArrayList<Song> songs;
    DownloadManager downloadManager;

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
    public void onBindViewHolder(@NonNull final ViewHoder viewHoder, int i) {
        final Song song= songs.get(i);
        viewHoder.txtnamesong.setText(song.getNamesong());
        viewHoder.txtsinger.setText(song.getSinger());
        Picasso.with(context).load(song.getImagesong()).into(viewHoder.imgsong);
        viewHoder.imgtheredot.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder= new MenuBuilder(context);
                MenuInflater menuInflater=new MenuInflater(context);
                menuInflater.inflate(R.menu.songmenu,menuBuilder);
                MenuPopupHelper menuPopupHelper= new MenuPopupHelper(context,menuBuilder,viewHoder.imgtheredot);
                menuPopupHelper.setForceShowIcon(true);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.songmenu_download:{

                                Toast.makeText(context,"download",Toast.LENGTH_LONG).show();
                                downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri =Uri.parse(song.getLinksong());
                                DownloadManager.Request request=new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference=downloadManager.enqueue(request);

                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {

                    }
                });
                menuPopupHelper.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtnamesong,txtsinger;
        ImageView imgsong,imgtheredot;


        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtnamesong=itemView.findViewById(R.id.dong_songlike_namesong);
            txtsinger=itemView.findViewById(R.id.dong_songlike_singer);
            imgsong=itemView.findViewById(R.id.dong_songlike_imagesong);
            imgtheredot=itemView.findViewById(R.id.dong_songlike_iconlike);
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
