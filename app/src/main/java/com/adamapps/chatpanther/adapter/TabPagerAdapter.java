package com.adamapps.chatpanther.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.adamapps.chatpanther.fragments.ChatFragment;
import com.adamapps.chatpanther.fragments.StatusFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ChatFragment();
            case 1:
                return new StatusFragment();
            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
