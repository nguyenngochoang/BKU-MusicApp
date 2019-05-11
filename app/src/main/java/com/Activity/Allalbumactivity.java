package com.Activity;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.Adapter.AllalbumAdapter;
import com.Model.Album;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Allalbumactivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<Album> albums;
    AllalbumAdapter allalbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allalbumactivity);
        anhxa();
        init();
        getdata();
    }

    private void getdata() {
        DataService dataService= APIservice.getservice();
        Call<List<Album>> callback=dataService.Getallalbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albums= (ArrayList<Album>) response.body();
                allalbumAdapter=new AllalbumAdapter(Allalbumactivity.this,albums);
                recyclerView.setLayoutManager(new GridLayoutManager(Allalbumactivity.this,2));
                recyclerView.setAdapter(allalbumAdapter);

            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });

    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Album");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.violet));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        recyclerView=findViewById(R.id.activity_allalbum_listallalbum);
        toolbar=findViewById(R.id.activity_allalbum_toolbar);
    }
}
