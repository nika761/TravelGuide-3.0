package com.example.travelguide.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.travelguide.fragments.UserHomeFragment;
import com.example.travelguide.fragments.UserProfileFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BottomNavigationPageAdapter extends FragmentPagerAdapter {


    private ArrayList<Fragment> fragments = new ArrayList<>();

    public BottomNavigationPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {

        fragments.add(fragment);

    }

}