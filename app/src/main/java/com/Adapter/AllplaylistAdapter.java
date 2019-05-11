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

//import com.example.silverp.musicdemo.Activity.Listsongactivity;
//import com.example.silverp.musicdemo.Model.Playlist;
//import com.example.silverp.musicdemo.R;
import com.Activity.Listsongactivity;
import com.Model.Playlist;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllplaylistAdapter extends RecyclerView.Adapter<AllplaylistAdapter.ViewHoder> {
    Context context;
    ArrayList<Playlist> playlists;

    public AllplaylistAdapter(Context context, ArrayList<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.dong_all_playlist,viewGroup,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        Playlist playlist=playlists.get(i);
        viewHoder.txtnameplaylist.setText(playlist.getNameplaylist());
        Picasso.with(context).load(playlist.getImageicon()).into(viewHoder.iconpalylist);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtnameplaylist;
        ImageView iconpalylist;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtnameplaylist=itemView.findViewById(R.id.dong_all_playlist_nameplaylist);
            iconpalylist=itemView.findViewById(R.id.dong_all_playlist_imageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Listsongactivity.class);
                    intent.putExtra("Playlist",playlists.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
