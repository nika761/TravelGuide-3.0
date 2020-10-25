package com.example.travelguide.ui.home.profile.userFollowActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

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
    private TextView userName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_follow);
        iniUI();
        setUserName();
        setClickListeners();
        initViewPager(viewPager);
    }

    private void iniUI() {
        tabLayout = findViewById(R.id.follower_page_tab_layout);
        viewPager = findViewById(R.id.follower_page_view_pager);
        backBtn = findViewById(R.id.follower_page_back_btn);
        userName = findViewById(R.id.follower_page_user_name);
    }

    private void initViewPager(ViewPager viewPager) {
        FollowPagerAdapter followPagerAdapter = new FollowPagerAdapter(getSupportFragmentManager());
        followPagerAdapter.addFragment(new FollowingFragment(), "Following");
        followPagerAdapter.addFragment(new FollowersFragment(), "Followers");
        viewPager.setAdapter(followPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    public void setUserName() {
        String name = getIntent().getStringExtra("user_name");

        if (name != null) {
            userName.setText(name);
        }
    }

    private void setClickListeners() {
        backBtn.setOnClickListener(v -> finish());
    }
}
