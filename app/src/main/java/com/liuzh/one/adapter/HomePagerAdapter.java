package com.liuzh.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 主页左右滑动viewpager的适配器
 * Created by 刘晓彬 on 2017/3/20.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;//所有的fragment

    public HomePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
