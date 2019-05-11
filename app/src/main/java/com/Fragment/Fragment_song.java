package com.Fragment;

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

//import com.example.silverp.musicdemo.Adapter.SonglikeAdapter;
//import com.example.silverp.musicdemo.Model.Song;
//import com.example.silverp.musicdemo.R;
//import com.example.silverp.musicdemo.Service.APIservice;
//import com.example.silverp.musicdemo.Service.DataService;

import com.Adapter.SonglikeAdapter;
import com.Model.Song;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_song extends Fragment {
    View view;
    ArrayList<Song> songs;
    RecyclerView recyclerView;
    SonglikeAdapter songlikeAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_songlike,container,false);
        recyclerView=view.findViewById(R.id.fragment_songlike_recyclerview);
        getdata();
        return view;
    }
    private void getdata(){
        DataService dataService= APIservice.getservice();
        Call<List<Song>> callback= dataService.GetSonglike();
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songs= (ArrayList<Song>) response.body();
                songlikeAdapter=new SonglikeAdapter(getActivity(),songs);
                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(songlikeAdapter);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
}
