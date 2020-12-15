package com.travel.guide.ui.music.favoriteMusic;

import com.travel.guide.model.response.AddFavoriteMusicResponse;
import com.travel.guide.model.response.FavoriteMusicResponse;

import java.util.List;

public interface FavoriteMusicListener {

    void onGetFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics);

    void onFavoriteRemoved(AddFavoriteMusicResponse addFavoriteMusicResponse);

    void onChooseRemoveFavorite(int musicId);

    void onError(String message);

}
