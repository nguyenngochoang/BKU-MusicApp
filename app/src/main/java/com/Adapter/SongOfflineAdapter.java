package com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Activity.Playermusic;
import com.Model.Song;
import com.StringUtils;
import com.example.firebase.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import okhttp3.internal.Util;

public class SongOfflineAdapter extends RecyclerView.Adapter<SongOfflineAdapter.ViewHoder> implements Filterable {
    ArrayList<Song> songs;
    ArrayList<Song> songsfull;
    Context context;

    public SongOfflineAdapter(ArrayList<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
        songsfull =new ArrayList<>();
        songsfull.addAll(songs);
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.dong_music_offline,parent,false);

        return new SongOfflineAdapter.ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        Song song= songs.get(position);
        holder.txtnamesong.setText(song.getNamesong());
        holder.txtsinger.setText(song.getSinger());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(songs.get(position).getLinksong());
        byte[] artbytes=mmr.getEmbeddedPicture();
        if(artbytes!=null){
            InputStream inputStream= new ByteArrayInputStream(mmr.getEmbeddedPicture());
            Bitmap bm= BitmapFactory.decodeStream(inputStream);
            holder.imageView.setImageBitmap(bm);
        }
        else
            holder.imageView.setImageResource(R.drawable.mp3filelocal);

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public Filter getFilter() {
        return songofffillter;
    }
    private Filter songofffillter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Song> filtersong =new ArrayList<>();
            if(constraint==null ||constraint.length()==0){
                filtersong.addAll(songsfull);
            }
            else
            {
                String filterPartren= StringUtils.removeAccent(constraint.toString());
                for(Song item:songsfull){
                    if(StringUtils.removeAccent(item.getNamesong()).contains(filterPartren)){
                        filtersong.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filtersong;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            songs.clear();
            songs.addAll((Collection<? extends Song>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtnamesong,txtsinger;
        ImageView imageView;
        public ViewHoder(View itemView) {
            super(itemView);
            txtnamesong=itemView.findViewById(R.id.dong_music_offline_name);
            txtsinger=itemView.findViewById(R.id.dong_music_offline_singer);
            imageView=itemView.findViewById((R.id.dong_music_offline_img));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Playermusic.class);
                    intent.putExtra("Songdevice",songs.get(getPosition()));

                    Toast.makeText(context,songs.get(getPosition()).getLinksong(),Toast.LENGTH_LONG).show();
                    context.startActivity(intent);
                }
            });
        }
    }
}
