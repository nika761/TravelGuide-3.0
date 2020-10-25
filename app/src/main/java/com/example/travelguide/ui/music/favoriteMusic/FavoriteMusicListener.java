package com.example.travelguide.ui.music.favoriteMusic;

import com.example.travelguide.model.response.FavoriteMusicResponse;

import java.util.List;

public interface FavoriteMusicListener {
    void onGetFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics);

    void onGetFavoriteFailed(String message);
}
