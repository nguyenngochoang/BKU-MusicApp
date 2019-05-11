package com.Adapter;

import android.app.DownloadManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Adapter_main_home_seach extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrfragment =new ArrayList<>();
    private ArrayList<String> arrtitle=new ArrayList<>();
    public Adapter_main_home_seach(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return arrfragment.get(i);
    }

    @Override
    public int getCount() {
        return arrfragment.size();
    }
    public  void addfragment(Fragment fragment,String title)
    {
        arrfragment.add(fragment);
        arrtitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrtitle.get(position);
    }
}
