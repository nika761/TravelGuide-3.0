package com.example.travelguide.ui.upload.presenter;

import com.example.travelguide.model.request.AddFavoriteMusic;
import com.example.travelguide.model.request.ByMoodRequest;
import com.example.travelguide.model.request.SearchMusicRequest;
import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.MoodResponse;
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
                iSearchMusic.onGetResponseError(t.getMessage());
            }
        });
    }


    public void addFavorite(String accessToken, AddFavoriteMusic addFavoriteMusic) {
        apiService.addFavoriteMusic(accessToken, addFavoriteMusic).enqueue(new Callback<AddFavoriteMusicResponse>() {
            @Override
            public void onResponse(Call<AddFavoriteMusicResponse> call, Response<AddFavoriteMusicResponse> response) {
                if (response.isSuccessful()) {
                    iSearchMusic.onFavoriteAdded(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddFavoriteMusicResponse> call, Throwable t) {
                iSearchMusic.onGetResponseError(t.getMessage());
            }
        });

    }

    public void getMoods(String accessToken) {
        apiService.getMoods(accessToken).enqueue(new Callback<MoodResponse>() {
            @Override
            public void onResponse(Call<MoodResponse> call, Response<MoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0 && response.body().getMoods().size() != 0) {
                        iSearchMusic.onGetMoods(response.body().getMoods());
                    } else {
                        iSearchMusic.onGetResponseError(response.message());
                    }
                } else {
                    iSearchMusic.onGetResponseError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MoodResponse> call, Throwable t) {
                iSearchMusic.onGetResponseError(t.getMessage());
            }
        });
    }

    public void getMusicByMood(String accessToken, ByMoodRequest byMoodRequest) {
        apiService.getMusicsByMood(accessToken, byMoodRequest).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0 && response.body().getAlbum().size() != 0) {
                        iSearchMusic.onGetMusic(response.body());
                    } else {
                        iSearchMusic.onGetResponseError(response.message());
                    }
                } else {
                    iSearchMusic.onGetResponseError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                iSearchMusic.onGetResponseError(t.getMessage());
            }
        });
    }

    public void searchMusic(String accessToken, SearchMusicRequest searchMusicRequest) {
        apiService.searchMusic(accessToken, searchMusicRequest).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    iSearchMusic.onGetMusic(response.body());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                iSearchMusic.onGetResponseError(t.getMessage());

            }
        });
    }
}
