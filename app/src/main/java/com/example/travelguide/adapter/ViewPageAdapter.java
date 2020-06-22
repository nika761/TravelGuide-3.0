package com.example.travelguide.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.travelguide.fragments.UserContentFragment;
import com.example.travelguide.fragments.UserPhotoFragment;
import com.example.travelguide.fragments.UserTourFragment;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private int numOfTab;

    public ViewPageAdapter(@NonNull FragmentManager fm, int numOfTab) {
        super(fm, numOfTab);
        this.numOfTab = numOfTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 1:
                return new UserContentFragment();

            case 2:
                return new UserTourFragment();

            case 0:
            default:
                return new UserPhotoFragment();
        }
    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}
