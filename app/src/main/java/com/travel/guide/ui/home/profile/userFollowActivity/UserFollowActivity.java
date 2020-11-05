package com.travel.guide.ui.home.profile.userFollowActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.R;
import com.travel.guide.ui.home.profile.userFollowActivity.follower.FollowersFragment;
import com.travel.guide.ui.home.profile.userFollowActivity.following.FollowingFragment;
import com.google.android.material.tabs.TabLayout;

public class UserFollowActivity extends AppCompatActivity {

    private TextView userName;

    private int customerUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_follow);


        if (getIntent().getIntExtra("customer_user_id", 0) != 0) {
            this.customerUserId = getIntent().getIntExtra("customer_user_id", 0);
        }

        initUI();
        initViewPager();
    }

    private void initUI() {
        userName = findViewById(R.id.follower_page_user_name);

        String name = getIntent().getStringExtra("user_name");
        if (name != null) {
            userName.setText(name);
        }

        ImageButton backBtn = findViewById(R.id.follower_page_back_btn);
        backBtn.setOnClickListener(v -> finish());

    }

    private void initViewPager() {
        TabLayout tabLayout = findViewById(R.id.follower_page_tab_layout);
        ViewPager viewPager = findViewById(R.id.follower_page_view_pager);

        FollowPagerAdapter followPagerAdapter = new FollowPagerAdapter(getSupportFragmentManager());
        followPagerAdapter.addFragment(new FollowingFragment(), "Following");
        followPagerAdapter.addFragment(new FollowersFragment(), "Followers");

        if (customerUserId != 0)
            followPagerAdapter.setCustomerUserId(customerUserId);

        viewPager.setAdapter(followPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

}
