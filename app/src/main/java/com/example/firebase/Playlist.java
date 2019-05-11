package com.example.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Playlist extends AppCompatActivity {


    User user;
    String playlistname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userInfo"); //userInfo
        playlistname = intent.getStringExtra("playlistnam"); // playlistname
    }
}
