package com.example.travelguide.ui.upload.interfaces;

import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.MusicResponse;

public interface ISearchMusic {
    void onGetMusic(MusicResponse musicResponse);

    void onPlayMusic(String music);

    void onStopMusic();

    void onPressMusic(String music, int position);

    void onFavoriteChoose(int musicId);

    void onFavoriteAdded(AddFavoriteMusicResponse addFavoriteMusicResponse);
}
