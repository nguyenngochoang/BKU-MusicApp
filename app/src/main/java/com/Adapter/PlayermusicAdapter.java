package com.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PlayermusicAdapter extends FragmentPagerAdapter {
    public  final ArrayList<Fragment> fragmentlist =new ArrayList<>();
    public PlayermusicAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }
    public void Addfragment(Fragment fragment){
        fragmentlist.add(fragment);
    }
}
