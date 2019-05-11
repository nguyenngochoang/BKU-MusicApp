package com.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.silverp.musicdemo.Model.Playlist;
//import com.example.silverp.musicdemo.R;
import com.Model.Playlist;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private List<Playlist> playlists;

    public PlaylistAdapter(Context context, int layout, List<Playlist> playlists) {
        this.context = context;
        this.layout = layout;
        this.playlists = playlists;
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class Viewhoder{
        TextView txtname;
        ImageView imgicon;
        ImageView imgbackground;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewhoder viewhoder;
        if(convertView==null) {
            viewhoder=new Viewhoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewhoder.txtname = convertView.findViewById(R.id.dong_playlist_namplaylist);
            viewhoder.imgicon = convertView.findViewById(R.id.dong_playlist_imageicon);
            viewhoder.imgbackground = convertView.findViewById(R.id.dong_playlist_background);
            convertView.setTag(viewhoder);
        }
        else
            viewhoder= (Viewhoder) convertView.getTag();
        Picasso.with(context).load(playlists.get(position).getImageicon()).into(viewhoder.imgicon);
        Picasso.with(context).load(playlists.get(position).getImagebackground()).into(viewhoder.imgbackground);
        viewhoder.txtname.setText(playlists.get(position).getNameplaylist());
        return convertView;
    }
}