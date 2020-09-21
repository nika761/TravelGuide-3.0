package com.example.travelguide.ui.music.searchMusic;

import com.example.travelguide.model.request.AddFavoriteMusic;
import com.example.travelguide.model.request.ByMoodRequest;
import com.example.travelguide.model.request.SearchMusicRequest;
import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.MoodResponse;
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMusicPresenter {
    private SearchMusicListener searchMusicListener;
    private ApiService apiService;

    public SearchMusicPresenter(SearchMusicListener searchMusicListener) {
        this.searchMusicListener = searchMusicListener;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getMusics(String accessToken) {
        apiService.getMusics(accessToken).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful()) {
                    searchMusicListener.onGetMusic(response.body());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                searchMusicListener.onGetResponseError(t.getMessage());
            }
        });
    }


    public void addFavorite(String accessToken, AddFavoriteMusic addFavoriteMusic) {
        apiService.addFavoriteMusic(accessToken, addFavoriteMusic).enqueue(new Callback<AddFavoriteMusicResponse>() {
            @Override
            public void onResponse(Call<AddFavoriteMusicResponse> call, Response<AddFavoriteMusicResponse> response) {
                if (response.isSuccessful()) {
                    searchMusicListener.onFavoriteAdded(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddFavoriteMusicResponse> call, Throwable t) {
                searchMusicListener.onGetResponseError(t.getMessage());
            }
        });

    }

    public void getMoods(String accessToken) {
        apiService.getMoods(accessToken).enqueue(new Callback<MoodResponse>() {
            @Override
            public void onResponse(Call<MoodResponse> call, Response<MoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0 && response.body().getMoods().size() != 0) {
                        searchMusicListener.onGetMoods(response.body().getMoods());
                    } else {
                        searchMusicListener.onGetResponseError(response.message());
                    }
                } else {
                    searchMusicListener.onGetResponseError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MoodResponse> call, Throwable t) {
                searchMusicListener.onGetResponseError(t.getMessage());
            }
        });
    }

    public void getMusicByMood(String accessToken, ByMoodRequest byMoodRequest) {
        apiService.getMusicsByMood(accessToken, byMoodRequest).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0 && response.body().getAlbum().size() != 0) {
                        searchMusicListener.onGetMusic(response.body());
                    } else {
                        searchMusicListener.onGetResponseError(response.message());
                    }
                } else {
                    searchMusicListener.onGetResponseError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                searchMusicListener.onGetResponseError(t.getMessage());
            }
        });
    }

    public void searchMusic(String accessToken, SearchMusicRequest searchMusicRequest) {
        apiService.searchMusic(accessToken, searchMusicRequest).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchMusicListener.onGetMusic(response.body());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                searchMusicListener.onGetResponseError(t.getMessage());

            }
        });
    }
}
