package com.example.travelguide.ui.upload.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.model.ItemMedia;
import com.example.travelguide.ui.upload.adapter.pager.MusicPagerAdapter;
import com.example.travelguide.ui.upload.fragment.FavoriteMusicFragment;
import com.example.travelguide.ui.upload.fragment.SearchMusicFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.travelguide.ui.upload.activity.EditPostActivity.STORIES_PATHS;

public class ChooseMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton backBtn;
    private TextView nextBtn;
    private List<ItemMedia> itemMediaList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        this.itemMediaList = (List<ItemMedia>) getIntent().getSerializableExtra(STORIES_PATHS);
        initUI();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUI() {

        tabLayout = findViewById(R.id.music_tab_layout);
        viewPager = findViewById(R.id.music_view_pager);

        nextBtn = findViewById(R.id.music_next_btn);
        nextBtn.setOnClickListener(this);

        backBtn = findViewById(R.id.music_back_btn);
        backBtn.setOnClickListener(this);

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
//                intent.putStringArrayListExtra("selectedPaths", storiesPath);
                intent.putExtra(STORIES_PATHS, (Serializable) itemMediaList);
                startActivity(intent);
                break;

            case R.id.music_back_btn:
                finish();
                break;
        }
    }
}
