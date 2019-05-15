package com.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.Adapter.SonglistAdapter;
import com.Model.AdverTising;
import com.Model.Album;
import com.Model.Playlist;
import com.Model.Song;
import com.Model.Style;
import com.Model.Theme;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Listsongactivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ImageView imageView;
    AdverTising adverTising;
    Playlist playlist;
    SonglistAdapter songlistAdapter;
    ArrayList<Song> songs;
    Style style;
    Theme theme;
    Album album;
    List<Integer>arrayID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsongactivity);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        anhxa();
        init();
        Dataintent();
        arrayID = new ArrayList<Integer>();
        Intent intent = getIntent();
        arrayID = (ArrayList<Integer>) intent.getSerializableExtra("arrayID");

        Toast.makeText(this,Long.toString(arrayID.size()),Toast.LENGTH_SHORT).show();


        if(adverTising!=null && !adverTising.getNamesong().equals(""))
        {
            setValueInView(adverTising.getNamesong(),adverTising.getImagesong());
            getDatasong(adverTising.getIdadvertising(),"adver");
        }
        if(playlist!=null && !playlist.getNameplaylist().equals(""))
        {
            setValueInView(playlist.getNameplaylist(),playlist.getImagebackground());
            getDatasong(playlist.getIdplaylist(),"playlist");
        }
        if(theme!=null && !theme.getNametheme().equals(""))
        {
            setValueInView(theme.getNametheme(),theme.getImagetheme());
            getDatasong(theme.getIdtheme(),"theme");
        }
        if(style!=null && !style.getNamestyle().equals(""))
        {
            setValueInView(style.getNamestyle(),style.getImagestyle());
            getDatasong(style.getIdstyle(),"style");
        }
        if(album!=null && !album.getNamealbum().equals(""))
        {
            setValueInView(album.getNamealbum(),album.getImagealbum());
            getDatasong(album.getIdalbum(),"album");
        }
//        setContextItem();

    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        if(v.getId()==R.id.activity_listsongactivity_listsongrecycerview)
//        {
//            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) menuInfo;
//
//        }
//    }
//
//    private void setContextItem() {
//        i
//    }

    private void getDatasong(String id, String type)  {
        DataService dataService= APIservice.getservice();
        Call<List<Song>> callback;
        if(type.equals("adver")) {
            callback= dataService.GetSongadvertising(id);
        }
        else if(type.equals("playlist"))
        {
            callback= dataService.GetSongplaylist(id);
        }
        else if(type.equals("style"))

        {
            callback=dataService.GetSongstyle(id);

        }
        else if(type.equals("theme"))
        {
            callback= dataService.GetSongtheme(id);
        }
        else
            callback= dataService.GetSongalbum(id);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songs= (ArrayList<Song>) response.body();
                songlistAdapter =new SonglistAdapter(Listsongactivity.this,songs);
                recyclerView.setLayoutManager(new LinearLayoutManager(Listsongactivity.this));
                recyclerView.setAdapter(songlistAdapter);

            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
        evenclick();

    }

    private void setValueInView(String name, String image) {
        collapsingToolbarLayout.setTitle(name);
        try {
            URL url =new URL(image);
            Bitmap bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable=new BitmapDrawable(getResources(),bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(image).into(imageView);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void anhxa() {
        imageView=findViewById(R.id.activity_listsongactivity_imglistsong);
        coordinatorLayout=findViewById(R.id.activity_listsongactivity_coordinatorlayout);
        collapsingToolbarLayout=findViewById(R.id.activity_listsongactivity_collapsingtoolbarlayout);
        toolbar=findViewById(R.id.activity_listsongactivity_toolbarlist);
        recyclerView=findViewById(R.id.activity_listsongactivity_listsongrecycerview);
        floatingActionButton=findViewById(R.id.activity_listsongactivity_floatingactionbutton);
    }

    private void Dataintent(){
        Intent intent=getIntent();
        if(intent!=null && intent.hasExtra("Banner"))
        {
            adverTising= (AdverTising) intent.getSerializableExtra("Banner");
            Toast.makeText(this,adverTising.getNamesong(),Toast.LENGTH_LONG).show();
        }
        if(intent!=null && intent.hasExtra("Playlist"))
        {
            playlist= (Playlist) intent.getSerializableExtra("Playlist");
            Toast.makeText(this,playlist.getNameplaylist(),Toast.LENGTH_LONG).show();
        }
        if(intent!=null && intent.hasExtra("Theme"))
        {
            theme= (Theme) intent.getSerializableExtra("Theme");
            Toast.makeText(this,theme.getNametheme(),Toast.LENGTH_LONG).show();
        }
        if(intent!=null && intent.hasExtra("Style"))
        {
            style= (Style) intent.getSerializableExtra("Style");
            Toast.makeText(this,style.getNamestyle(),Toast.LENGTH_LONG).show();
        }
        if(intent!=null && intent.hasExtra("Album"))
        {
            album= (Album) intent.getSerializableExtra("Album");
            Toast.makeText(this,album.getNamealbum(),Toast.LENGTH_LONG).show();
        }

    }
    private  void evenclick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Listsongactivity.this,Playermusic.class);
                intent.putExtra("Songlist",songs);
                startActivity(intent);
            }
        });
    }


}
