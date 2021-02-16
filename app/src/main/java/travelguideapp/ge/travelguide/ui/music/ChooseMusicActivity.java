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
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.model.customModel.ItemMedia;
import travelguideapp.ge.travelguide.model.parcelable.MediaFileData;
import travelguideapp.ge.travelguide.ui.music.favoriteMusic.FavoriteMusicFragment;
import travelguideapp.ge.travelguide.ui.music.searchMusic.SearchMusicFragment;
import travelguideapp.ge.travelguide.ui.upload.UploadPostActivity;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.ui.editPost.EditPostActivity;

public class ChooseMusicActivity extends AppCompatActivity implements View.OnClickListener, SearchMusicFragment.OnChooseMusic, PlayMusicListener {

    public static final String MUSIC_ID = "music_id";

    //    private List<ItemMedia> itemMediaList = new ArrayList<>();
    private List<MediaFileData> mediaFiles = new ArrayList<>();
    private int playingPosition, musicId;

    private MediaPlayer musicPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_music);
        SystemManager.setLanguage(this);
//        this.itemMediaList = (List<ItemMedia>) getIntent().getSerializableExtra(EditPostActivity.STORIES_PATHS);
        try {
            this.mediaFiles = getIntent().getParcelableArrayListExtra(MediaFileData.INTENT_KEY_MEDIA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();
    }

    private void initUI() {

        TextView nextBtn = findViewById(R.id.music_next_btn);
        nextBtn.setOnClickListener(this);

        ImageButton backBtn = findViewById(R.id.music_back_btn);
        backBtn.setOnClickListener(this);

        ViewPager viewPager = findViewById(R.id.music_view_pager);

        MusicPagerAdapter musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager());
        musicPagerAdapter.addFragment(SearchMusicFragment.getInstance(this), getString(R.string.music));
        musicPagerAdapter.addFragment(FavoriteMusicFragment.getInstance(this), getString(R.string.favorites));
        viewPager.setAdapter(musicPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.music_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_next_btn:
                Intent intent = new Intent(this, UploadPostActivity.class);
//                intent.putExtra(EditPostActivity.STORIES_PATHS, (Serializable) itemMediaList);
                intent.putParcelableArrayListExtra(MediaFileData.INTENT_KEY_MEDIA, (ArrayList<? extends Parcelable>) mediaFiles);
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
    public void onChooseMusicToPlay(String music, int position) {
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
        super.onPause();
        stopPlayer();
    }

    @Override
    protected void onDestroy() {
        stopPlayer();
        super.onDestroy();
    }
}
