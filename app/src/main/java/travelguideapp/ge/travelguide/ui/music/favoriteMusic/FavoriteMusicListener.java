package travelguideapp.ge.travelguide.ui.music.favoriteMusic;

import travelguideapp.ge.travelguide.model.response.AddFavoriteMusicResponse;
import travelguideapp.ge.travelguide.model.response.FavoriteMusicResponse;

import java.util.List;

public interface FavoriteMusicListener {

    void onGetFavoriteMusics(List<FavoriteMusicResponse.Favotite_musics> favoriteMusics);

    void onFavoriteRemoved(AddFavoriteMusicResponse addFavoriteMusicResponse);

    void onChooseRemoveFavorite(int musicId);

    void onError(String message);

}
