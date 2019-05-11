package com.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.example.silverp.musicdemo.Activity.AllplaylistActivity;
//import com.example.silverp.musicdemo.Activity.Listsongactivity;
//import com.example.silverp.musicdemo.Activity.MainActivity;
//import com.example.silverp.musicdemo.Adapter.PlaylistAdapter;
//import com.example.silverp.musicdemo.Model.AdverTising;
//import com.example.silverp.musicdemo.Model.Playlist;
//import com.example.silverp.musicdemo.R;
//import com.example.silverp.musicdemo.Service.APIservice;
//import com.example.silverp.musicdemo.Service.DataService;

import com.Activity.AllplaylistActivity;
import com.Activity.Listsongactivity;
import com.Adapter.PlaylistAdapter;
import com.Model.Playlist;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_playlist extends Fragment {
    View view;
    ListView lvplaylist;
    TextView txttitleallplaylist,txtmoreplaylist;
    List<Playlist> playlists;
    PlaylistAdapter playlistAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_playlist,container,false);
        anhxa();
        getData();
        txtmoreplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AllplaylistActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void anhxa(){
        lvplaylist =view.findViewById(R.id.fragment_playlist_listplaylist);
        txttitleallplaylist=view.findViewById(R.id.fragment_playlist_titleall);
        txtmoreplaylist=view.findViewById(R.id.fragment_playlist_more);


    }
    private void getData(){
        DataService dataService= APIservice.getservice();
        Call<List<Playlist>> callback= dataService.GetPlaylistDay();
        callback.enqueue(new Callback<List<Playlist>>() {


            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                playlists= (List<Playlist>) response.body();
                playlistAdapter=new PlaylistAdapter(getActivity(),R.layout.dong_playlist,playlists);
                lvplaylist.setAdapter(playlistAdapter);
                setListViewHeightBasedOnChildren(lvplaylist);
                lvplaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent =new Intent(getActivity(), Listsongactivity.class);
                        intent.putExtra("Playlist",playlists.get(position));
                        startActivity(intent);

                    }
                });


            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
