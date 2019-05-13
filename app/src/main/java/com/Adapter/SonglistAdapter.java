package com.Adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ListActivity;
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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.Activity.Playermusic;
import com.Model.Playlist;
import com.Model.Song;
import com.example.firebase.R;
//import com.example.silverp.musicdemo.Activity.PlaymusicActivity;
//import com.example.silverp.musicdemo.Model.Song;
//import com.example.silverp.musicdemo.R;
import com.example.firebase.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SonglistAdapter extends RecyclerView.Adapter<SonglistAdapter.ViewHoder> {
    Context context;
    ArrayList<Song> songs;
    DownloadManager downloadManager;
    User user;


    public SonglistAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;

    }

    public SonglistAdapter(User user){
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_songlist,viewGroup,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder viewHoder, final int i) {
        final Song song=songs.get(i);
        viewHoder.txtindex.setText(i+1+"");
        viewHoder.txtname.setText(song.getNamesong());
        viewHoder.txtsinger.setText(song.getSinger());
        viewHoder.imglike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder= new MenuBuilder(context);
                MenuInflater menuInflater=new MenuInflater(context);
                menuInflater.inflate(R.menu.songmenu,menuBuilder);
                MenuPopupHelper menuPopupHelper= new MenuPopupHelper(context,menuBuilder,viewHoder.imglike);
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
                            case R.id.songmenu_addplaylist:{

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
        TextView txtindex,txtname,txtsinger;
        ImageView imglike;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtindex=itemView.findViewById(R.id.dong_songlist_index);
            txtname=itemView.findViewById(R.id.dong_songlist_namesong);
            txtsinger=itemView.findViewById(R.id.dong_songlist_singer);
            imglike=itemView.findViewById(R.id.dong_songlist_likeimg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Playermusic.class);
                    intent.putExtra("Song",songs.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }

}
