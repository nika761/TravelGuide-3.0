package com.travelguide.travelguide.ui.home.profile.userFollowActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FollowPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> fragmentsTitle = new ArrayList<>();
    private int customerUserId = 0;

    public FollowPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (customerUserId != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("customer_user_id", customerUserId);
            fragments.get(position).setArguments(bundle);
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentsTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }

    public void setCustomerUserId(int customerUserId) {
        this.customerUserId = customerUserId;
    }
}
