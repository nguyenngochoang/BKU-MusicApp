package com.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.silverp.musicdemo.Activity.Listsongactivity;
//import com.example.silverp.musicdemo.Model.AdverTising;
//import com.example.silverp.musicdemo.R;
import com.Activity.Listsongactivity;
import com.Model.AdverTising;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<AdverTising> arradver;

    public BannerAdapter(Context context, ArrayList<AdverTising> arradver) {
        this.context = context;
        this.arradver = arradver;
    }

    @Override
    public int getCount() {
        return arradver.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_banner,null);
        ImageView imgbackgroundbanner= view.findViewById(R.id.dong_banner_background);
        ImageView imgSongbanner=view.findViewById(R.id.dong_banner_imagesong);
        TextView txtnamesong=view.findViewById(R.id.dong_banner_namesong);
        TextView txtcontext=view.findViewById(R.id.dong_banner_textcontextdecription);
        Picasso.with(context).load(arradver.get(position).getImageadver()).into(imgbackgroundbanner);
        Picasso.with(context).load(arradver.get(position).getImagesong()).into(imgSongbanner);
        txtnamesong.setText(arradver.get(position).getNamesong());
        txtcontext.setText(arradver.get(position).getContentadver());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, Listsongactivity.class);
                intent.putExtra("Banner",arradver.get(position));
                context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
