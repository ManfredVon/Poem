package com.fmf.poem.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fmf.poem.poem.PoemConstant;

/**
 * Created by fmf on 15/6/2.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public TabPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int pos) {
        return fragments[pos];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        Bundle args = fragments[pos].getArguments();
        String pageTitle = args.getString(PoemConstant.PAGE_TITLE);
        return pageTitle;
    }

}