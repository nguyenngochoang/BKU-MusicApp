package com.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_disk extends Fragment {
    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_disk,container,false);
        circleImageView=view.findViewById(R.id.fragment_disk_circleimage);
        circleImageView.setImageResource(R.drawable.disk);
        objectAnimator=ObjectAnimator.ofFloat(circleImageView,"rotation",0f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        return view;
    }
    public void setimagecircle(String url){
        Picasso.with(getActivity()).load(url).into(circleImageView);

    }
    public void setimagelocalcircle(Bitmap bitmap){
        circleImageView.setImageBitmap(bitmap);
    }
    public void setimagelocalcircleresouce(){
        circleImageView.setImageResource(R.drawable.disk);
    }
}
