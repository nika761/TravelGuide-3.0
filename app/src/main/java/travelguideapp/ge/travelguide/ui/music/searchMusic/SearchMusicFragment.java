package travelguideapp.ge.travelguide.ui.music.searchMusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.ui.music.PlayMusicListener;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.AddFavoriteMusic;
import travelguideapp.ge.travelguide.model.request.ByMoodRequest;
import travelguideapp.ge.travelguide.model.request.SearchMusicRequest;
import travelguideapp.ge.travelguide.model.response.AddFavoriteMusicResponse;
import travelguideapp.ge.travelguide.model.response.MoodResponse;
import travelguideapp.ge.travelguide.model.response.MusicResponse;
import travelguideapp.ge.travelguide.ui.music.MusicMoodsAdapter;

import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;


public class SearchMusicFragment extends Fragment implements SearchMusicListener {

    public static SearchMusicFragment getInstance(PlayMusicListener playMusicListener) {
        SearchMusicFragment searchMusicFragment = new SearchMusicFragment();
        searchMusicFragment.playMusicListener = playMusicListener;
        return searchMusicFragment;
    }

    private PlayMusicListener playMusicListener;

    private RecyclerView searchMusicRecycler, moodsRecycler;
    private SearchMusicAdapter searchMusicAdapter;
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

        searchMusicPresenter.getMusics();

        searchMusicPresenter.getMoods();

        RxTextView.textChanges(searchField)
                .debounce(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .subscribe((Consumer<CharSequence>) charSequence -> {
                    if (searchMusicPresenter != null)
                        if (!charSequence.toString().isEmpty()) {
                            searchMusicPresenter.searchMusic(new SearchMusicRequest(charSequence.toString()));
                        } else {
                            searchMusicPresenter.getMusics();
                        }
                });
    }

    @Override
    public void onGetMusic(MusicResponse musicResponse) {
        try {
            if (searchMusicAdapter == null) {
                searchMusicAdapter = new SearchMusicAdapter(this, playMusicListener);
                searchMusicAdapter.setMusics(musicResponse.getAlbum());
                searchMusicRecycler.setAdapter(searchMusicAdapter);
            } else {
                searchMusicAdapter.setMusics(musicResponse.getAlbum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStopMusic() {
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchMusicPresenter != null)
            searchMusicPresenter.getMusics();
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
        searchMusicPresenter.addFavorite(addFavoriteMusic);
    }

    @Override
    public void onFavoriteAdded(AddFavoriteMusicResponse addFavoriteMusicResponse) {
        switch (addFavoriteMusicResponse.getStatus()) {
            case 0:
//                Toast.makeText(getContext(), "Music removed from favorites", Toast.LENGTH_SHORT).show();
                break;

            case 1:
//                Toast.makeText(getContext(), "Music add to favorites", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onGetMoods(List<MoodResponse.Moods> moods) {
        moodsRecycler.setAdapter(new MusicMoodsAdapter(moods, this));
    }

    @Override
    public void onGetError(String message) {
        MyToaster.getToast(moodsRecycler.getContext(), message);
    }

    @Override
    public void onChooseMood(int moodId) {
        searchMusicPresenter.getMusicByMood(new ByMoodRequest(moodId));
    }

    private void play(String music) {
        Uri uri = Uri.parse(music);
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer.create(getContext(), uri);
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
