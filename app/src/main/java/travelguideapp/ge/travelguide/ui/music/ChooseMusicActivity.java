package travelguideapp.ge.travelguide.ui.music;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.model.parcelable.MediaFileParams;
import travelguideapp.ge.travelguide.ui.music.favoriteMusic.FavoriteMusicFragment;
import travelguideapp.ge.travelguide.ui.music.searchMusic.SearchMusicFragment;
import travelguideapp.ge.travelguide.ui.upload.UploadPostActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChooseMusicActivity extends BaseActivity implements SearchMusicFragment.OnChooseMusic, PlayMusicListener {

    public static final String MUSIC_ID = "music_id";

    private List<MediaFileParams> mediaFiles = new ArrayList<>();
    private int playingPosition, musicId;

    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        try {
            this.mediaFiles = getIntent().getParcelableArrayListExtra(MediaFileParams.MEDIA_FILE_PARAMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();
    }

    private void initUI() {

        TextView nextBtn = findViewById(R.id.music_next_btn);
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseMusicActivity.this, UploadPostActivity.class);
            intent.putParcelableArrayListExtra(MediaFileParams.MEDIA_FILE_PARAMS, (ArrayList<? extends Parcelable>) mediaFiles);
            intent.putExtra(MUSIC_ID, musicId);
            startActivity(intent);
        });

        ImageButton backBtn = findViewById(R.id.music_back_btn);
        backBtn.setOnClickListener(v -> finish());

        ViewPager viewPager = findViewById(R.id.music_view_pager);

        MusicPagerAdapter musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager());
        musicPagerAdapter.addFragment(SearchMusicFragment.getInstance(this), getString(R.string.music));
        musicPagerAdapter.addFragment(FavoriteMusicFragment.getInstance(this), getString(R.string.favorites));
        viewPager.setAdapter(musicPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.music_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onMusicChoose(int musicId) {
        this.musicId = musicId;
    }

    private void play(String music) {
        Uri uri = Uri.parse(music);
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer.create(this, uri);
            musicPlayer.setOnCompletionListener(mp -> stopPlayer());
        }
        musicPlayer.start();
    }

    private void stopPlayer() {
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    @Override
    public void onChooseMusicForPlay(String music, int position) {
        if (musicPlayer != null && musicPlayer.isPlaying()) {
            if (playingPosition == position) {
                stopPlayer();
            } else {
                stopPlayer();
                play(music);
            }
        } else {
            play(music);
        }
        playingPosition = position;
    }

    @Override
    public void onChooseMusicForPost(int musicId) {
        this.musicId = musicId;
    }

    @Override
    public void onPause() {
        stopPlayer();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopPlayer();
        super.onDestroy();
    }
}
