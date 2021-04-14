package travelguideapp.ge.travelguide.ui.music.favoriteMusic;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.AddFavoriteMusic;
import travelguideapp.ge.travelguide.model.response.AddFavoriteMusicResponse;
import travelguideapp.ge.travelguide.model.response.FavoriteMusicResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FavoriteMusicPresenter {
    private final FavoriteMusicListener favoriteMusicListener;
    private final ApiService apiService;

    FavoriteMusicPresenter(FavoriteMusicListener favoriteMusicListener) {
        this.favoriteMusicListener = favoriteMusicListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getFavoriteMusics() {
        apiService.getFavoriteMusics().enqueue(new Callback<FavoriteMusicResponse>() {
            @Override
            public void onResponse(@NotNull Call<FavoriteMusicResponse> call, @NotNull Response<FavoriteMusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        if (response.body().getFavotite_musics().size() > 0)
                            favoriteMusicListener.onGetFavoriteMusics(response.body().getFavotite_musics());
                    } else {
                        favoriteMusicListener.onError(response.message());
                    }
                } else {
                    favoriteMusicListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FavoriteMusicResponse> call, @NotNull Throwable t) {
                favoriteMusicListener.onError(t.getMessage());
            }
        });
    }

    void removeFavorite(AddFavoriteMusic addFavoriteMusic) {
        apiService.addFavoriteMusic(addFavoriteMusic).enqueue(new Callback<AddFavoriteMusicResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddFavoriteMusicResponse> call, @NotNull Response<AddFavoriteMusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favoriteMusicListener.onFavoriteRemoved(response.body());
                } else {
                    favoriteMusicListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddFavoriteMusicResponse> call, @NotNull Throwable t) {
                favoriteMusicListener.onError(t.getMessage());
            }
        });

    }
}
