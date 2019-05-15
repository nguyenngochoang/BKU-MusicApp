package com.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.Adapter.Adapter_main_home_seach;
import com.Fragment.Fragment_home_page;
import com.Fragment.Fragment_seach;
import com.example.firebase.R;
import com.example.firebase.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OnlineActivity extends AppCompatActivity {
    TabLayout maintablayout;
    ViewPager mainviewpager;
    public static User user;
    public static List<String> playlists;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        anhxa();
        init();

        Intent intent = getIntent();
        try{
            user = (User) intent.getSerializableExtra("userInfo");
        }catch (Exception e){

        }

        if(user!=null)
            loadUserPlaylist(); //read all the playlists name

    }
    private void anhxa(){
        maintablayout=(TabLayout) findViewById(R.id.main_tablayout);
        mainviewpager=(ViewPager) findViewById(R.id.main_viewpager);
    }
    private void init(){
        Adapter_main_home_seach adapter_main_home_seach=new Adapter_main_home_seach(getSupportFragmentManager());
        adapter_main_home_seach.addfragment(new Fragment_home_page(),"Trang chủ");
        adapter_main_home_seach.addfragment(new Fragment_seach(),"Tìm kiếm");
        mainviewpager.setAdapter(adapter_main_home_seach);
        maintablayout.setupWithViewPager(mainviewpager);
        maintablayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        maintablayout.getTabAt(1).setIcon(R.drawable.iconsearch);
    }


    //read all the playlists name
    private void loadUserPlaylist(){
        playlists = new ArrayList<String>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(user!=null){
            mDatabase.child(user.name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild("playlist")){
                        for(DataSnapshot ds : dataSnapshot.child("playlist").getChildren()){
                            String name = ds.child("name").getValue().toString();
                            playlists.add(name);
                            Log.w("testing",name);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}

