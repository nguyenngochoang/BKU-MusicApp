package com.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Activity.Playermusic;
import com.Adapter.PlaymusicAdapter;
import com.example.firebase.R;

public class fragment_listmusic_player extends Fragment {
    View view;
    RecyclerView recyclerView;
    PlaymusicAdapter playmusicAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_listmusic_player,container,false);
        recyclerView=view.findViewById(R.id.fragment_listmusic_player_list);
        if(Playermusic.songs.size()>0) {
            playmusicAdapter = new PlaymusicAdapter(getActivity(), Playermusic.songs);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(playmusicAdapter);
        }
        return view;
    }
}
