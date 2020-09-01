package com.example.travelguide.ui.upload.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.AddFavoriteMusic;
import com.example.travelguide.model.request.ByMoodRequest;
import com.example.travelguide.model.request.SearchMusicRequest;
import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.MoodResponse;
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.ui.upload.adapter.recycler.MoodsAdapter;
import com.example.travelguide.ui.upload.adapter.recycler.SearchMusicAdapter;
import com.example.travelguide.ui.upload.interfaces.ISearchMusic;
import com.example.travelguide.ui.upload.presenter.SearchMusicPresenter;
import com.jakewharton.rxbinding4.leanback.RxSearchEditText;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class SearchMusicFragment extends Fragment implements ISearchMusic {
    private RecyclerView searchMusicRecycler, moodsRecycler;
    private SearchMusicPresenter searchMusicPresenter;
    private int position;
    private int playingPosition;
    private MediaPlayer musicPlayer;
    private EditText searchField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_music, container, false);
        searchMusicRecycler = view.findViewById(R.id.search_music_recycler);
        moodsRecycler = view.findViewById(R.id.moods_recycler);
        searchField = view.findViewById(R.id.search_fragment_search_field);
        searchMusicPresenter = new SearchMusicPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchMusicPresenter.getMusics(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken
                (searchMusicRecycler.getContext()));

        searchMusicPresenter.getMoods(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken
                (searchMusicRecycler.getContext()));

        // Input listener with control operation interval, that is, the interval event between each input event listener must be greater than 1200ms (1s20ms)
        RxTextView.textChanges(searchField)
                .debounce(1200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                //CharSequence is converted to String
                .map(CharSequence::toString)
                .subscribe((Consumer<CharSequence>) charSequence -> {
                    if (charSequence.toString().isEmpty()) {
                        searchMusicPresenter.getMusics(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken
                                (searchMusicRecycler.getContext()));
                    }
                    searchMusicPresenter.searchMusic(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken(moodsRecycler.getContext()), new SearchMusicRequest(charSequence.toString()));

                });

//        searchField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                searchMusicPresenter.searchMusic(HelperPref.getCurrentAccessToken(moodsRecycler.getContext()), new SearchMusicRequest(s.toString()));
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().isEmpty()) {
//                    searchMusicPresenter.getMusics(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken
//                            (searchMusicRecycler.getContext()));
//                }
//
//                searchMusicPresenter.searchMusic(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken(moodsRecycler.getContext()), new SearchMusicRequest(s.toString()));
//            }
//        });
    }

    @Override
    public void onGetMusic(MusicResponse musicResponse) {
        if (musicResponse.getStatus() == 0) {
            searchMusicRecycler.setLayoutManager(new LinearLayoutManager(searchMusicRecycler.getContext()));
            SearchMusicAdapter searchMusicAdapter = new SearchMusicAdapter(musicResponse.getAlbum(), this);
            searchMusicRecycler.setAdapter(searchMusicAdapter);
        }
    }


    @Override
    public void onPlayMusic(String music) {
//        play(music);
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
        searchMusicPresenter.addFavorite("Bearer" + " " + HelperPref.getCurrentAccessToken
                (searchMusicRecycler.getContext()), addFavoriteMusic);
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
        moodsRecycler.setLayoutManager(new LinearLayoutManager(moodsRecycler.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        moodsRecycler.setAdapter(new MoodsAdapter(moods, this));
    }

    @Override
    public void onGetResponseError(String message) {

    }

    @Override
    public void onChooseMood(int moodId) {
        searchMusicPresenter.getMusicByMood(ACCESS_TOKEN_BEARER +
                HelperPref.getCurrentAccessToken(moodsRecycler.getContext()), new ByMoodRequest(moodId));
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

//    private void stop() {
//        stopPlayer();
//    }

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
        super.onDestroy();
    }
}
