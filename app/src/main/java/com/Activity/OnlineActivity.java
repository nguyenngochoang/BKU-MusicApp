package com.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Adapter.Adapter_main_home_seach;
import com.Fragment.Fragment_home_page;
import com.Fragment.Fragment_seach;
import com.example.firebase.R;

public class OnlineActivity extends AppCompatActivity {
    TabLayout maintablayout;
    ViewPager mainviewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        anhxa();
        init();
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
}

