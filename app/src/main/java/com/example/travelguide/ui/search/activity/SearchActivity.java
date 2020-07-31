package com.example.travelguide.ui.search.activity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.ui.search.adapter.pager.SearchPagerAdapter;
import com.example.travelguide.ui.search.fragment.GoFragment;
import com.example.travelguide.ui.search.fragment.HashtagsFragment;
import com.example.travelguide.ui.search.fragment.SearchStoriesFragment;
import com.example.travelguide.ui.search.fragment.SearchUsersFragment;
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
        setClickListeners();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUI() {
        tabLayout = findViewById(R.id.search_tabs);
        viewPager = findViewById(R.id.search_view_pager);
        backBtn = findViewById(R.id.search_back_btn_second);
        searchBtn = findViewById(R.id.search_btn);
    }

    private void setClickListeners() {
        backBtn.setOnClickListener(v -> finish());
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
