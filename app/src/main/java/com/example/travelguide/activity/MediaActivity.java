package com.example.travelguide.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.adapter.pager.MediaPagerAdapter;
import com.example.travelguide.fragments.MediaFragment;
import com.google.android.material.tabs.TabLayout;

public class MediaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_gallery);
        iniUI();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void iniUI() {
        tabLayout = findViewById(R.id.media_tab);
        viewPager = findViewById(R.id.media_view_pager);
    }


    private void setViewPager(ViewPager viewPager) {
        MediaPagerAdapter mediaPagerAdapter = new MediaPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mediaPagerAdapter);
    }
}
