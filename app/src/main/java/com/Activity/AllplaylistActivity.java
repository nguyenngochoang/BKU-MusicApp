package com.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.Adapter.AllplaylistAdapter;
import com.Model.Playlist;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllplaylistActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Playlist> playlists;
    AllplaylistAdapter allplaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allplaylist);
        anhxa();
        init();
        getData();
    }

    private void getData() {
        DataService dataService= APIservice.getservice();
        Call<List<Playlist>> callback=dataService.Getallplaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                playlists= (ArrayList<Playlist>) response.body();
                allplaylistAdapter=new AllplaylistAdapter(AllplaylistActivity.this,playlists);
                recyclerView.setLayoutManager(new GridLayoutManager(AllplaylistActivity.this,2));
                recyclerView.setAdapter(allplaylistAdapter);

            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Play Lists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.violet));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar=findViewById(R.id.activity_allplaylist_toolbar);
        recyclerView=findViewById(R.id.activity_allplaylist_listallplaylist);
    }
}
