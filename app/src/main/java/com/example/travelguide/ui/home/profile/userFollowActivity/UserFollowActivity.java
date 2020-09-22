package com.example.travelguide.ui.home.profile.userFollowActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.ui.home.profile.userFollowActivity.follower.FollowersFragment;
import com.example.travelguide.ui.home.profile.userFollowActivity.following.FollowingFragment;
import com.google.android.material.tabs.TabLayout;

public class UserFollowActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_follow);
        iniUI();
        setClickListeners();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void iniUI() {
        tabLayout = findViewById(R.id.follower_page_tab_layout);
        viewPager = findViewById(R.id.follower_page_view_pager);
        backBtn = findViewById(R.id.follower_page_back_btn);
    }

    private void setViewPager(ViewPager viewPager) {
        int userId = HelperPref.getCurrentUserId(this);
        FollowPagerAdapter followPagerAdapter = new FollowPagerAdapter(getSupportFragmentManager());
        followPagerAdapter.addFragment(new FollowingFragment(userId), "Following");
        followPagerAdapter.addFragment(new FollowersFragment(userId), "Followers");
        viewPager.setAdapter(followPagerAdapter);
    }

    private void setClickListeners() {
        backBtn.setOnClickListener(v -> finish());
    }
}
