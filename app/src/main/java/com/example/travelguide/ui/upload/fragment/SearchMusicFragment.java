package com.example.travelguide.ui.upload.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.AddFavoriteMusic;
import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.ui.upload.adapter.recycler.SearchMusicAdapter;
import com.example.travelguide.ui.upload.interfaces.ISearchMusic;
import com.example.travelguide.ui.upload.presenter.SearchMusicPresenter;

public class SearchMusicFragment extends Fragment implements ISearchMusic {
    private RecyclerView searchMusicRecycler;
    private SearchMusicPresenter searchMusicPresenter;
    private int position;
    private int playingPosition;
    private MediaPlayer musicPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_music, container, false);
        searchMusicRecycler = view.findViewById(R.id.search_music_recycler);
        searchMusicPresenter = new SearchMusicPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchMusicPresenter.getMusics("Bearer" + " " + HelperPref.getCurrentAccessToken
                (searchMusicRecycler.getContext()));
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
