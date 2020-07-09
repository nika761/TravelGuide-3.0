package com.example.travelguide.adapter.pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.travelguide.fragments.MediaFragment;

import java.util.ArrayList;

public class MediaPagerAdapter extends FragmentPagerAdapter {

    public MediaPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_image", position == 0 ? false : true);
        MediaFragment mediaFragment = new MediaFragment();
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Video" : "Photos";
    }
}
