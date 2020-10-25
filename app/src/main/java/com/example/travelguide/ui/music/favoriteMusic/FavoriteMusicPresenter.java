package com.example.travelguide.ui.music.favoriteMusic;

import android.util.Log;

import com.example.travelguide.model.response.FavoriteMusicResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FavoriteMusicPresenter {
    private FavoriteMusicListener favoriteMusicListener;
    private ApiService apiService;

    FavoriteMusicPresenter(FavoriteMusicListener favoriteMusicListener) {
        this.favoriteMusicListener = favoriteMusicListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getFavoriteMusics(String accessToken) {
        apiService.getFavoriteMusics(accessToken).enqueue(new Callback<FavoriteMusicResponse>() {
            @Override
            public void onResponse(Call<FavoriteMusicResponse> call, Response<FavoriteMusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        favoriteMusicListener.onGetFavoriteMusics(response.body().getFavotite_musics());
                    } else {
                        favoriteMusicListener.onGetFavoriteFailed(response.message());
                    }
                } else {
                    favoriteMusicListener.onGetFavoriteFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<FavoriteMusicResponse> call, Throwable t) {
                favoriteMusicListener.onGetFavoriteFailed(t.getMessage());
            }
        });
    }
}
