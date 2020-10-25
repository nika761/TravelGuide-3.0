package com.example.travelguide.ui.music;

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
import com.example.travelguide.ui.music.favoriteMusic.FavoriteMusicFragment;
import com.example.travelguide.ui.music.searchMusic.SearchMusicFragment;
import com.example.travelguide.ui.upload.UploadPostActivity;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.travelguide.ui.editPost.EditPostActivity.STORIES_PATHS;

public class ChooseMusicActivity extends AppCompatActivity implements View.OnClickListener, SearchMusicFragment.OnChooseMusic {
    public static final String MUSIC_ID = "music_id";
    private List<ItemMedia> itemMediaList = new ArrayList<>();
    private int musicId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        this.itemMediaList = (List<ItemMedia>) getIntent().getSerializableExtra(STORIES_PATHS);
        initUI();
    }

    private void initUI() {

        TextView nextBtn = findViewById(R.id.music_next_btn);
        nextBtn.setOnClickListener(this);

        ImageButton backBtn = findViewById(R.id.music_back_btn);
        backBtn.setOnClickListener(this);

        ViewPager viewPager = findViewById(R.id.music_view_pager);

        MusicPagerAdapter musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager());
        musicPagerAdapter.addFragment(new SearchMusicFragment(), "Music");
        musicPagerAdapter.addFragment(new FavoriteMusicFragment(), "Favorites");
        viewPager.setAdapter(musicPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.music_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_next_btn:
                Intent intent = new Intent(this, UploadPostActivity.class);
                intent.putExtra(STORIES_PATHS, (Serializable) itemMediaList);
                intent.putExtra(MUSIC_ID, musicId);
                startActivity(intent);
                break;

            case R.id.music_back_btn:
                finish();
                break;
        }
    }

    @Override
    public void onMusicChoose(int musicId) {
        this.musicId = musicId;
    }
}
