package com.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.Adapter.SongOfflineAdapter;
import com.Model.Song;
import com.example.firebase.R;

import java.util.ArrayList;

public class OfflineActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Song> songs;
    SearchView searchView;
    SongOfflineAdapter songOfflineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        anhxa();

        init();
        if(ContextCompat.checkSelfPermission(OfflineActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(OfflineActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(OfflineActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

            }
            else{
                ActivityCompat.requestPermissions(OfflineActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
        else{
            dostuff();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reseachmenu,menu);
        MenuItem menuItem=menu.findItem(R.id.reseach);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                songOfflineAdapter.getFilter().filter(newText);
                return true;
//                return false;
            }
        });
        return true;
    }

    private void dostuff() {
        getmusic();
        songOfflineAdapter =new SongOfflineAdapter(songs,OfflineActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(OfflineActivity.this));
        recyclerView.setAdapter(songOfflineAdapter);
    }

    private void getmusic() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        // Iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()) {
            // Get columns

            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int urlcollumn=musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);



            do {
                String thisurl = musicCursor.getString(urlcollumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songs.add(new Song(thisTitle,thisurl,thisArtist));
            }
            while (musicCursor.moveToNext());
        }
    }
    private void anhxa(){
        songs=new ArrayList<>();
        recyclerView=findViewById(R.id.activity_offline_recycler);
        toolbar=findViewById(R.id.activity_offline_toolbar);
    }
    private void init() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.off_music);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.violet));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchView.isIconified())
                    searchView.onActionViewCollapsed();
//
                else
                    finish();


            }
        });



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(OfflineActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
                        dostuff();
                    }
                }
                else
                {
                    Toast.makeText(this,"No permission",Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }
}
