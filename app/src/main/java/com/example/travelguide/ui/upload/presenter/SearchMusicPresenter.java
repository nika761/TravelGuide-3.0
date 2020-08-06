package com.example.travelguide.ui.upload.presenter;

import com.example.travelguide.model.request.AddFavoriteMusic;
import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.upload.interfaces.ISearchMusic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMusicPresenter {
    private ISearchMusic iSearchMusic;
    private ApiService apiService;

    public SearchMusicPresenter(ISearchMusic iSearchMusic) {
        this.iSearchMusic = iSearchMusic;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getMusics(String accessToken) {
        apiService.getMusics(accessToken).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful()) {
                    iSearchMusic.onGetMusic(response.body());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {

            }
        });
    }


    public void addFavorite(String accessToken, AddFavoriteMusic addFavoriteMusic) {
        apiService.addFavoriteMusic(accessToken, addFavoriteMusic).enqueue(new Callback<AddFavoriteMusicResponse>() {
            @Override
            public void onResponse(Call<AddFavoriteMusicResponse> call, Response<AddFavoriteMusicResponse> response) {
                if (response.isSuccessful()){
                    iSearchMusic.onFavoriteAdded(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddFavoriteMusicResponse> call, Throwable t) {

            }
        });

    }
}
