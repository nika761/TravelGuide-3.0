package com.example.travelguide.upload.adapter.pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.travelguide.upload.fragment.GalleryFragment;

public class GalleryPagerAdapter extends FragmentPagerAdapter {

    public GalleryPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_image", position == 0 ? false : true);
        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setArguments(bundle);
        return galleryFragment;
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
