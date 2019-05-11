package com.Fragment;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.example.silverp.musicdemo.Activity.Allalbumactivity;
//import com.example.silverp.musicdemo.Adapter.AlbumAdapter;
//import com.example.silverp.musicdemo.Model.Album;
//import com.example.silverp.musicdemo.R;
//import com.example.silverp.musicdemo.Service.APIservice;
//import com.example.silverp.musicdemo.Service.DataService;

import com.Activity.Allalbumactivity;
import com.Adapter.AlbumAdapter;
import com.Model.Album;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_album extends Fragment {
    View view;
    ArrayList<Album> albumshot;
    RecyclerView recyclerView;
    TextView txtmorealbum;
    AlbumAdapter albumAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_album,container,false);
        anhxa();
        getdata();
        txtmorealbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), Allalbumactivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void anhxa(){
        recyclerView=view.findViewById(R.id.fragment_album_recyclerview);
        txtmorealbum=view.findViewById(R.id.fragment_album_more);
    }
    private  void getdata(){
        DataService dataService= APIservice.getservice();
        Call<List<Album>> callback= dataService.GetAlbumhot();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albumshot= (ArrayList<Album>) response.body();
                albumAdapter=new AlbumAdapter(getActivity(),albumshot);
                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(albumAdapter);

            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}
