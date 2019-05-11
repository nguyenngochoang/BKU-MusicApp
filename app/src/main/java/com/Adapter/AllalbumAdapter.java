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
//import com.example.silverp.musicdemo.Model.Album;
//import com.example.silverp.musicdemo.R;
import com.Activity.Listsongactivity;
import com.Model.Album;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllalbumAdapter  extends RecyclerView.Adapter<AllalbumAdapter.ViewHoder> {
    Context context;
    ArrayList<Album> albums;

    public AllalbumAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.dong_all_playlist,viewGroup,false);
        return (new ViewHoder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        Album album =albums.get(i);
        viewHoder.txtname.setText(album.getNamealbum());
        viewHoder.txtartist.setText(album.getNamesingeralbum());
        Picasso.with(context).load(album.getImagealbum()).into(viewHoder.imgalbum);

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtname,txtartist;
        ImageView imgalbum;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.dong_all_playlist_nameplaylist);
            txtartist=itemView.findViewById(R.id.dong_all_playlist_artist);
            imgalbum=itemView.findViewById(R.id.dong_all_playlist_imageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, Listsongactivity.class);
                    intent.putExtra("Album",albums.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }

    }
}
