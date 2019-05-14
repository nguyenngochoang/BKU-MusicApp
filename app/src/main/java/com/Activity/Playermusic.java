package com.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.Adapter.PlayermusicAdapter;
import com.Adapter.PlaymusicAdapter;
import com.Fragment.Fragment_disk;
import com.Fragment.fragment_listmusic_player;
import com.Model.Song;
import com.example.firebase.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;


public class Playermusic extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttimesong, txtcurtime;
    SeekBar seekBar;
    ImageButton imgtnext, imgpre, imgrepeat, imgplay, imgrandom;
    ViewPager viewPager;
    Song song;
    int position=0;
    boolean repeat=false;
    boolean checkrandom=false;
    boolean next=false;
    public static ArrayList<Song> songs;
    public static PlayermusicAdapter adapter;
    Fragment_disk fragment_disk;
    fragment_listmusic_player fragmentListmusicPlayer;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playermusic);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            songs.clear();
        }
        getdataintent();
        anhxa();

        init();
        eventclick();

    }

    private void eventclick() {
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapter.getItem(1)!=null)
                {
                    if(songs.size()>0)
                    {   Intent intent=getIntent();
                        if(intent.hasExtra("Songdevice")) {
                            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                            mmr.setDataSource(songs.get(0).getLinksong());
                            byte[] artbytes=mmr.getEmbeddedPicture();
                            if(artbytes!=null) {
                                InputStream inputStream = new ByteArrayInputStream(mmr.getEmbeddedPicture());
                                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                                fragment_disk.setimagelocalcircle(bm);
                            }
                            else
                                fragment_disk.setimagelocalcircleresouce();
                        }
                        else
                            fragment_disk.setimagecircle(songs.get(0).getImagesong());
                        handler.removeCallbacks(this);
                    }
                    else
                        handler.postDelayed(this,300);
                }
            }
        },500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.iconplay);
                }
                else
                {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.iconpause);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat==true) {
                    repeat = false;
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                }
                else {
                    if(checkrandom==true) {
                        checkrandom = false;
                        imgrandom.setImageResource(R.drawable.iconsuffle);
                    }
                    repeat = true;
                    imgrepeat.setImageResource(R.drawable.iconsyned);
                }


            }
        });
        imgrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkrandom==true) {
                    checkrandom = false;
                    imgrandom.setImageResource(R.drawable.iconsuffle);
                }
                else {
                    if(repeat==true) {
                        repeat = false;
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    checkrandom = true;
                    imgrandom.setImageResource(R.drawable.iconshuffled);
                }


            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
        imgtnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextsong();


            }


        });
        imgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songs.size()>0){
                    if(mediaPlayer!=null)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                    if(checkrandom)
                    {
                        Random random= new Random();
                        int index=random.nextInt(songs.size());
                        if(index==position)
                            position=index-1;
                        else
                            position=index;
                        if(position<0)
                            position=songs.size()-1;
                    }
                    else if(!checkrandom && !repeat)
                    {
                        position--;
                        if(position<0)
                            position=songs.size()-1;
                    }
                    new PlayMp3().execute(songs.get(position).getLinksong());
                    fragment_disk.setimagecircle(songs.get(position).getImagesong());
                    getSupportActionBar().setTitle(songs.get(position).getNamesong());
                    imgplay.setImageResource(R.drawable.iconpause);
                    updatime();



                }

                imgpre.setClickable(false);
                imgtnext.setClickable(false);
                Handler handler1=new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpre.setClickable(true);
                        imgtnext.setClickable(true);

                    }
                },1000);
            }

        });
    }

    private void nextsong() {
        if(songs.size()>0){
            if(mediaPlayer!=null)
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
            }
            if(checkrandom)
            {
                Random random= new Random();
                int index=random.nextInt(songs.size());
                if(index==position)
                    position=index-1;
                else
                    position=index;
                if(position<0)
                    position=songs.size()-1;
            }
            else if(!checkrandom && !repeat)
            {
                position++;
                if(position>songs.size()-1)
                    position=0;
            }
            new PlayMp3().execute(songs.get(position).getLinksong());
            if(songs.get(position).getImagesong()!=null) {
                fragment_disk.setimagecircle(songs.get(position).getImagesong());
            }
            getSupportActionBar().setTitle(songs.get(position).getNamesong());
            imgplay.setImageResource(R.drawable.iconpause);
            updatime();



        }
        imgpre.setClickable(false);
        imgtnext.setClickable(false);
        Handler handler1=new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgpre.setClickable(true);
                imgtnext.setClickable(true);

            }
        },1000);
    }

    private void getdataintent() {
        Intent intent=getIntent();
        songs=new ArrayList<>();
        if(intent!=null)
        {
            if(intent.hasExtra("Song"))
            {
                song=intent.getParcelableExtra("Song");
                songs.add(song);

            }
            if(intent.hasExtra("Songlist"))
            {
                songs=intent.getParcelableArrayListExtra("Songlist");

            }
            if(intent.hasExtra("Songdevice"))
            {
                song=intent.getParcelableExtra("Songdevice");
                songs.add(song);

            }
        }

    }

    private void init() {
        fragment_disk=new Fragment_disk();
        fragmentListmusicPlayer=new fragment_listmusic_player();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        adapter=new PlayermusicAdapter(getSupportFragmentManager());

        adapter.Addfragment(fragmentListmusicPlayer);
        adapter.Addfragment(fragment_disk);
        viewPager.setAdapter(adapter);
        fragment_disk= (Fragment_disk) adapter.getItem(1);
        if(songs.size()>0){
            getSupportActionBar().setTitle(songs.get(0).getNamesong());
            new PlayMp3().execute(songs.get(0).getLinksong());
            imgplay.setImageResource(R.drawable.iconpause);
        }
    }

    private void anhxa() {
        toolbar= findViewById(R.id.activity_playmusic_toolbar);
        txtcurtime=findViewById(R.id.activity_playmusic_curtime);
        txttimesong=findViewById(R.id.activity_playmusic_songtime);
        imgplay=findViewById(R.id.activity_playmusic_play);
        imgtnext=findViewById(R.id.activity_playmusic_next);
        imgpre=findViewById(R.id.activity_playmusic_preview);
        imgrandom=findViewById(R.id.activity_playmusic_suffle);
        imgrepeat=findViewById(R.id.activity_playmusic_repeat);
        viewPager=findViewById(R.id.activity_playmusic_viewpager);
        seekBar=findViewById(R.id.activity_playmusic_seekbar);
    }
    class PlayMp3 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            try {
            super.onPostExecute(s);
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
                Uri uri =Uri.parse(s);
                mediaPlayer.setDataSource(Playermusic.this,uri);
                mediaPlayer.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            Timesong();
            updatime();
        }
    }

    private void Timesong() {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("mm:ss");
        txttimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void updatime(){
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null)
                {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("mm:ss");
                    txtcurtime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next=true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            }
        },300);
        final Handler handler1=new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next==true){
                    nextsong();
                    next=false;
                    handler1.removeCallbacks(this);
                }
                else
                    handler1.postDelayed(this,1000);
            }
        },1000);
    }


}
