package com.example.travelguide.activity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.adapter.pager.SearchPagerAdapter;
import com.example.travelguide.fragments.SearchGoFragment;
import com.example.travelguide.fragments.SearchHashtagsFragment;
import com.example.travelguide.fragments.SearchStoriesFragment;
import com.example.travelguide.fragments.SearchUsersFragment;
import com.example.travelguide.utils.UtilsUI;
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
        searchPagerAdapter.addFragment(new SearchHashtagsFragment(), "HASHTAGS");
        searchPagerAdapter.addFragment(new SearchGoFragment(), "GO !");
        viewPager.setAdapter(searchPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
