package com.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.Adapter.BannerAdapter;
import com.Model.AdverTising;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;
//import com.example.silverp.musicdemo.Adapter.BannerAdapter;
//import com.example.silverp.musicdemo.Model.AdverTising;
//import com.example.silverp.musicdemo.R;
//import com.example.silverp.musicdemo.Service.APIservice;
//import com.example.silverp.musicdemo.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int curitem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_banner,container,false);
        anhxa();
        getData();
        return view;
    }
    private void getData(){
        DataService dataService= APIservice.getservice();
        Call<List<AdverTising>> callback=dataService.GetDataBanner();
        callback.enqueue(new Callback<List<AdverTising>>() {
            @Override
            public void onResponse(Call<List<AdverTising>> call, Response<List<AdverTising>> response) {
                ArrayList<AdverTising> banners= (ArrayList<AdverTising>) response.body();
                bannerAdapter=new BannerAdapter(getActivity(),banners);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler=new Handler();
                runnable=new Runnable() {
                    @Override
                    public void run() {
                        curitem=viewPager.getCurrentItem();
                        curitem++;
                        if(curitem>=viewPager.getAdapter().getCount())
                        {
                            curitem=0;
                        }
                        viewPager.setCurrentItem(curitem,true);
                        handler.postDelayed(runnable,4500);
                    }
                };
                handler.postDelayed(runnable,4500);
            }

            @Override
            public void onFailure(Call<List<AdverTising>> call, Throwable t) {

            }
        });
    }
    private void anhxa(){
        viewPager=view.findViewById(R.id.fragment_banner_viewpager);
        circleIndicator=view.findViewById(R.id.fragment_banner_circleindicator);
    }
}
