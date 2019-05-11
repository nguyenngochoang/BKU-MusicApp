package com.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.SonglikeAdapter;
import com.Model.Song;
import com.Service.APIservice;
import com.Service.DataService;
import com.StringUtils;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import com.example.silverp.musicdemo.R;

public class Fragment_seach extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView kocodulieu;
    ArrayList<Song> songs;
    SonglikeAdapter songlikeAdapter;
    SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_seach,container,false);
        toolbar=view.findViewById(R.id.fragment_seach_toolbar);
        recyclerView=view.findViewById(R.id.fragment_seach_listsong);
        kocodulieu=view.findViewById(R.id.kocodulieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search Music");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.violet));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchView.isIconified())
                    searchView.onActionViewCollapsed();
//
                else
                    ((AppCompatActivity)getActivity()).finish();


            }
        });



        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reseachmenu,menu);
        MenuItem menuItem=menu.findItem(R.id.reseach);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String standalize= StringUtils.removeAccent(query);
                Log.d("okm",standalize);
                Seachtukhoa(standalize);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;

            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void Seachtukhoa(String key){
        DataService dataService= APIservice.getservice();
        Call<List<Song>> callback=dataService.GetSongseach(key);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {

                songs= (ArrayList<Song>) response.body();
                Toast.makeText(getActivity(),songs.size()+"",Toast.LENGTH_LONG).show();
                if(songs.size()>0){
                    songlikeAdapter=new SonglikeAdapter(getActivity(),songs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(songlikeAdapter);
                    kocodulieu.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else
                {
                    kocodulieu.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Toast.makeText(getActivity(),"cc1",Toast.LENGTH_LONG).show();
            }
        });

    }
}
