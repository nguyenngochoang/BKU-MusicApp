package com.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Model.Song;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class PlaymusicAdapter extends RecyclerView.Adapter<PlaymusicAdapter.ViewHoder> {
    Context context;
    ArrayList<Song> songs;

    public PlaymusicAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_playermusic,parent,false);

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        Song song= songs.get(position);
        holder.txtsinger.setText(song.getSinger());
        holder.txtindex.setText(position+1+"");
        holder.txtname.setText(song.getNamesong());

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtname,txtsinger,txtindex;

        public ViewHoder(View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.dong_playermusic_name);
            txtindex=itemView.findViewById(R.id.dong_playermusic_index);
            txtsinger=itemView.findViewById(R.id.dong_playermusic_singer);
        }
    }
}
