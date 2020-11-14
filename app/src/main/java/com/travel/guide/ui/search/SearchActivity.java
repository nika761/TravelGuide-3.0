package com.travel.guide.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.travel.guide.R;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends AppCompatActivity {

    private ImageButton searchBtn;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUI();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUI() {
        tabLayout = findViewById(R.id.search_tabs);
        viewPager = findViewById(R.id.search_view_pager);
        backBtn = findViewById(R.id.search_back_btn_second);
        backBtn.setOnClickListener(v -> finish());
        searchBtn = findViewById(R.id.search_btn);
    }


    private void setViewPager(ViewPager viewPager) {
        SearchPagerAdapter searchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        searchPagerAdapter.addFragment(new SearchStoriesFragment(), "STORIES");
        searchPagerAdapter.addFragment(new SearchUsersFragment(), "USERS");
        searchPagerAdapter.addFragment(new HashtagsFragment(), "HASHTAGS");
        searchPagerAdapter.addFragment(new GoFragment(), "GO !");
        viewPager.setAdapter(searchPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
