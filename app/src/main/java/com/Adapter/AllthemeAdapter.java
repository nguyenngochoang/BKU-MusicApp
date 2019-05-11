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

import com.Activity.Listsongactivity;
import com.Model.Theme;
//import com.example.silverp.musicdemo.Activity.Listsongactivity;
//import com.example.silverp.musicdemo.Model.Theme;
//import com.example.silverp.musicdemo.R;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllthemeAdapter extends RecyclerView.Adapter<AllthemeAdapter.ViewHoder> {
    Context context;
    ArrayList<Theme> themes;

    public AllthemeAdapter(Context context, ArrayList<Theme> themes) {
        this.context = context;
        this.themes = themes;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.dong_all_theme_or_style,viewGroup,false);
        return (new ViewHoder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        Theme theme=themes.get(i);
        viewHoder.txtname.setText(theme.getNametheme());
        Picasso.with(context).load(theme.getImagetheme()).into(viewHoder.imageView);

    }

    @Override
    public int getItemCount() {
        return themes.size();
    }

    public class  ViewHoder extends RecyclerView.ViewHolder{
        TextView txtname;
        ImageView imageView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.dong_all_theme_or_style_name);
            imageView=itemView.findViewById(R.id.dong_all_theme_or_style_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, Listsongactivity.class);
                    intent.putExtra("Theme",themes.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
}
