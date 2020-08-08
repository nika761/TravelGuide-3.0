package com.example.travelguide.ui.upload.presenter;

import android.util.Log;

import com.example.travelguide.model.response.FavoriteMusicResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.upload.interfaces.IFavoriteMusic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMusicPresenter {
    private IFavoriteMusic iFavoriteMusic;
    private ApiService apiService;

    public FavoriteMusicPresenter(IFavoriteMusic iFavoriteMusic) {
        this.iFavoriteMusic = iFavoriteMusic;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getFavoriteMusics(String accessToken) {
        apiService.getFavoriteMusics(accessToken).enqueue(new Callback<FavoriteMusicResponse>() {
            @Override
            public void onResponse(Call<FavoriteMusicResponse> call, Response<FavoriteMusicResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        iFavoriteMusic.onGetFavoriteMusics(response.body().getFavotite_musics());
                        Log.e("cxzcx", "asdasdasd");
                    } else {
                        iFavoriteMusic.onGetFavoriteFailed();
                        Log.e("cxzcx", "asdaqweqqwesdasd");
                    }
                } else {
                    Log.e("cxzcx", response.message());
                }
            }

            @Override
            public void onFailure(Call<FavoriteMusicResponse> call, Throwable t) {
                iFavoriteMusic.onGetFavoriteFailed();
                Log.e("cxzcx", "zxczxccccccc");
            }
        });
    }
}
