package com.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.example.silverp.musicdemo.Activity.AllthemeandStyleActivity;
//import com.example.silverp.musicdemo.Activity.Listsongactivity;
//import com.example.silverp.musicdemo.Model.Style;
//import com.example.silverp.musicdemo.Model.Styletheme;
//import com.example.silverp.musicdemo.Model.Theme;
//import com.example.silverp.musicdemo.R;
//import com.example.silverp.musicdemo.Service.APIservice;
//import com.example.silverp.musicdemo.Service.DataService;
import com.Activity.AllthemeandStyleActivity;
import com.Activity.Listsongactivity;
import com.Model.Style;
import com.Model.Styletheme;
import com.Model.Theme;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_themestyle extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    Styletheme styletheme;
    TextView txtseeall;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_styletheme,container,false);
        horizontalScrollView=view.findViewById(R.id.fragment_styletheme_horizontalscrollview);
        txtseeall=view.findViewById(R.id.fragment_styletheme_more);
        txtseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllthemeandStyleActivity.class);
                startActivity(intent);
            }
        });
        getdata();
        return view;
    }
    private void getdata(){
        DataService dataService= APIservice.getservice();
        Call<Styletheme> callback=dataService.Getstylethemetoday();
        callback.enqueue(new Callback<Styletheme>() {
            @Override
            public void onResponse(Call<Styletheme> call, Response<Styletheme> response) {
                styletheme = response.body();

                final ArrayList<Style> arrStyle = (ArrayList<Style>) styletheme.getStyle();
                final ArrayList<Theme> arrtheme= (ArrayList<Theme>) styletheme.getTheme();

                LinearLayout linearLayout=new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layout=new LinearLayout.LayoutParams(300,300);
                layout.setMargins(10,20,10,30);
                for(int i=0;i<arrStyle.size();i++){
                    CardView cardView=new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView= new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(arrStyle.get(i).getImagestyle()!=null){
                        Picasso.with(getActivity()).load(arrStyle.get(i).getImagestyle()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int fni=i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Listsongactivity.class);
                            intent.putExtra("Style",arrStyle.get(fni));
                            startActivity(intent);
                        }
                    });

                }
                for(int j=0;j<arrtheme.size();j++){
                    CardView cardView=new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView= new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(arrtheme.get(j).getImagetheme()!=null){
                        Picasso.with(getActivity()).load(arrtheme.get(j).getImagetheme()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int fnj=j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),Listsongactivity.class);
                            intent.putExtra("Theme",arrtheme.get(fnj));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<Styletheme> call, Throwable t) {

            }
        });
    }

}
