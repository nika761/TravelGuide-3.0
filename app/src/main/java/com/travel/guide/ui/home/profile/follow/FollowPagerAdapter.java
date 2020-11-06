package com.travel.guide.ui.home.profile.follow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.travel.guide.enums.FollowType;

import java.util.ArrayList;

public class FollowPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> fragmentsTitle = new ArrayList<>();
    private int customerUserId;

    FollowPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        FollowFragment followFragment = new FollowFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("request_type", position == 0 ? FollowType.FOLLOWING : FollowType.FOLLOWER);
        bundle.putInt("customer_user_id", customerUserId);
        followFragment.setArguments(bundle);
        return followFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    void setTitles(String titleFirst, String titleSecond) {
        fragmentsTitle.add(0, titleFirst);
        fragmentsTitle.add(1, titleSecond);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? fragmentsTitle.get(0) : fragmentsTitle.get(1);
    }

    void setCustomerUserId(int customerUserId) {
        this.customerUserId = customerUserId;
    }

}
