package com.travel.guide.ui.music.searchMusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.request.AddFavoriteMusic;
import com.travel.guide.model.request.ByMoodRequest;
import com.travel.guide.model.request.SearchMusicRequest;
import com.travel.guide.model.response.AddFavoriteMusicResponse;
import com.travel.guide.model.response.MoodResponse;
import com.travel.guide.model.response.MusicResponse;
import com.travel.guide.ui.music.MusicMoodsAdapter;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class SearchMusicFragment extends Fragment implements SearchMusicListener {
    private RecyclerView searchMusicRecycler, moodsRecycler;
    private EditText searchField;

    private SearchMusicPresenter searchMusicPresenter;
    private MediaPlayer musicPlayer;

    private int playingPosition;
    private int musicId;

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_music, container, false);
        searchMusicPresenter = new SearchMusicPresenter(this);

        searchMusicRecycler = view.findViewById(R.id.search_music_recycler);
        searchMusicRecycler.setLayoutManager(new LinearLayoutManager(searchMusicRecycler.getContext()));
        searchMusicRecycler.setHasFixedSize(true);

        moodsRecycler = view.findViewById(R.id.moods_recycler);
        moodsRecycler.setLayoutManager(new LinearLayoutManager(moodsRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false));

        searchField = view.findViewById(R.id.search_fragment_search_field);

//        searchMusicRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
////            @Override
////            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
////                super.onScrollStateChanged(recyclerView, newState);
////                switch (newState){
////                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
////
////                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
////                        hide();
////                        break;
////
////                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
////                        show();
////                        break;
////                }
////
////            }
////
////            @Override
////            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
////                super.onScrolled(recyclerView, dx, dy);
////                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
////                    hide();
////                    controlsVisible = false;
////                    scrolledDistance = 0;
////                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
////                    show();
////                    controlsVisible = true;
////                    scrolledDistance = 0;
////                }
////
////                if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
////                    scrolledDistance += dy;
////                }
////            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchMusicPresenter.getMusics(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(searchMusicRecycler.getContext()));

        searchMusicPresenter.getMoods(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(searchMusicRecycler.getContext()));

        RxTextView.textChanges(searchField)
                .debounce(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .subscribe((Consumer<CharSequence>) charSequence -> {
                    if (!charSequence.toString().isEmpty()) {
                        searchMusicPresenter.searchMusic(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(moodsRecycler.getContext()), new SearchMusicRequest(charSequence.toString()));
                    }
                });
    }

    @Override
    public void onGetMusic(MusicResponse musicResponse) {
        searchMusicRecycler.setAdapter(new SearchMusicAdapter(musicResponse.getAlbum(), this, searchField.getContext()));
    }

    private void hide() {
//        constraintLayout.animate().translationY(-searchField.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void show() {
//        constraintLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    public void onStopMusic() {
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    @Override
    public void onPressMusic(String music, int position) {
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
    public void onFavoriteChoose(int musicId) {
        AddFavoriteMusic addFavoriteMusic = new AddFavoriteMusic(musicId);
        searchMusicPresenter.addFavorite("Bearer" + " " + HelperPref.getAccessToken(searchMusicRecycler.getContext()), addFavoriteMusic);
    }

    @Override
    public void onFavoriteAdded(AddFavoriteMusicResponse addFavoriteMusicResponse) {
        switch (addFavoriteMusicResponse.getStatus()) {
            case 0:
                Toast.makeText(getContext(), "Music removed from favorites", Toast.LENGTH_SHORT).show();
                break;

            case 1:
                Toast.makeText(getContext(), "Music add to favorites", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onGetMoods(List<MoodResponse.Moods> moods) {
        moodsRecycler.setAdapter(new MusicMoodsAdapter(moods, this));
    }

    @Override
    public void onGetError(String message) {
        Toast.makeText(moodsRecycler.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChooseMood(int moodId) {
        searchMusicPresenter.getMusicByMood(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(moodsRecycler.getContext()), new ByMoodRequest(moodId));
    }

    private void play(String music) {
        Uri uri = Uri.parse(music);
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer.create(getContext(), uri);
            musicPlayer.setOnCompletionListener(mp -> stopPlayer());
        }
        musicPlayer.start();
    }

    public void pause() {
        if (musicPlayer != null) {
            musicPlayer.pause();
        }
    }

    private void stopPlayer() {
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlayer();
    }

    @Override
    public void onDestroy() {
        if (searchMusicPresenter != null) {
            searchMusicPresenter = null;
        }
        stopPlayer();
        super.onDestroy();
    }

    public interface OnChooseMusic {

        void onMusicChoose(int musicId);

    }

}
