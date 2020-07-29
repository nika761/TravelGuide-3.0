package com.example.travelguide.upload.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.profile.adapter.pager.FollowersPagerAdapter;
import com.example.travelguide.profile.fragment.UserFollowersFragment;
import com.example.travelguide.profile.fragment.UserFollowingFragment;
import com.example.travelguide.upload.adapter.pager.MusicPagerAdapter;
import com.example.travelguide.upload.fragment.FavoriteMusicFragment;
import com.example.travelguide.upload.fragment.SearchMusicFragment;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChooseMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView nextBtn;
    private ArrayList<String> storiesPath = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        this.storiesPath = getIntent().getStringArrayListExtra("selectedPaths");
        initUI();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUI() {
        tabLayout = findViewById(R.id.music_tab_layout);
        viewPager = findViewById(R.id.music_view_pager);
        nextBtn = findViewById(R.id.music_next_btn);
        nextBtn.setOnClickListener(this);
    }

    private void setViewPager(ViewPager viewPager) {
        MusicPagerAdapter musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager());
        musicPagerAdapter.addFragment(new SearchMusicFragment(), "Music");
        musicPagerAdapter.addFragment(new FavoriteMusicFragment(), "Favorites");
        viewPager.setAdapter(musicPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_next_btn:
                Intent intent = new Intent(this, DescribePostActivity.class);
                intent.putStringArrayListExtra("selectedPaths", storiesPath);
                startActivity(intent);
                break;
        }
    }
}
