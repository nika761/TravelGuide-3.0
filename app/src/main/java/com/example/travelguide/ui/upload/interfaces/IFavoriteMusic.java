package com.example.travelguide.ui.upload.interfaces;

import com.example.travelguide.model.response.FavoriteMusicResponse;

import java.util.List;

public interface IFavoriteMusic {
    void onGetFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics);

    void onGetFavoriteFailed();
}
