package com.example.travelguide.ui.music.favoriteMusic;

import android.util.Log;

import com.example.travelguide.model.response.FavoriteMusicResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMusicPresenter {
    private FavoriteMusicListener favoriteMusicListener;
    private ApiService apiService;

    public FavoriteMusicPresenter(FavoriteMusicListener favoriteMusicListener) {
        this.favoriteMusicListener = favoriteMusicListener;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getFavoriteMusics(String accessToken) {
        apiService.getFavoriteMusics(accessToken).enqueue(new Callback<FavoriteMusicResponse>() {
            @Override
            public void onResponse(Call<FavoriteMusicResponse> call, Response<FavoriteMusicResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        favoriteMusicListener.onGetFavoriteMusics(response.body().getFavotite_musics());
                        Log.e("cxzcx", "asdasdasd");
                    } else {
                        favoriteMusicListener.onGetFavoriteFailed();
                        Log.e("cxzcx", "asdaqweqqwesdasd");
                    }
                } else {
                    Log.e("cxzcx", response.message());
                }
            }

            @Override
            public void onFailure(Call<FavoriteMusicResponse> call, Throwable t) {
                favoriteMusicListener.onGetFavoriteFailed();
                Log.e("cxzcx", "zxczxccccccc");
            }
        });
    }
}
