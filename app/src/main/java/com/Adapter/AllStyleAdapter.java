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
import com.Model.Style;
//import com.example.silverp.musicdemo.Activity.Listsongactivity;
//import com.example.silverp.musicdemo.Model.Style;
//import com.example.silverp.musicdemo.R;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class AllStyleAdapter extends RecyclerView.Adapter<AllStyleAdapter.ViewHoder> {
    Context context;
    ArrayList<Style> styles;

    public AllStyleAdapter(Context context, ArrayList<Style> styles) {
        this.context = context;
        this.styles = styles;
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
        Style style =styles.get(i);
        viewHoder.txtname.setText(style.getNamestyle());
        Picasso.with(context).load(style.getImagestyle()).into(viewHoder.imageView);

    }

    @Override
    public int getItemCount() {
        return styles.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtname;
        ImageView imageView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.dong_all_theme_or_style_name);
            imageView=itemView.findViewById(R.id.dong_all_theme_or_style_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Listsongactivity.class);
                    intent.putExtra("Style",styles.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
