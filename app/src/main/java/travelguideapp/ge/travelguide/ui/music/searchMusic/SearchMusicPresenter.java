package travelguideapp.ge.travelguide.ui.music.searchMusic;

import travelguideapp.ge.travelguide.model.request.AddFavoriteMusic;
import travelguideapp.ge.travelguide.model.request.ByMoodRequest;
import travelguideapp.ge.travelguide.model.request.SearchMusicRequest;
import travelguideapp.ge.travelguide.model.response.AddFavoriteMusicResponse;
import travelguideapp.ge.travelguide.model.response.MoodResponse;
import travelguideapp.ge.travelguide.model.response.MusicResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SearchMusicPresenter {
    private SearchMusicListener searchMusicListener;
    private ApiService apiService;

    SearchMusicPresenter(SearchMusicListener searchMusicListener) {
        this.searchMusicListener = searchMusicListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getMusics(String accessToken) {
        apiService.getMusics(accessToken).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        searchMusicListener.onGetMusic(response.body());
                } else {
                    searchMusicListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                searchMusicListener.onGetError(t.getMessage());
            }
        });
    }


    void addFavorite(String accessToken, AddFavoriteMusic addFavoriteMusic) {
        apiService.addFavoriteMusic(accessToken, addFavoriteMusic).enqueue(new Callback<AddFavoriteMusicResponse>() {
            @Override
            public void onResponse(Call<AddFavoriteMusicResponse> call, Response<AddFavoriteMusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchMusicListener.onFavoriteAdded(response.body());
                } else {
                    searchMusicListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddFavoriteMusicResponse> call, Throwable t) {
                searchMusicListener.onGetError(t.getMessage());
            }
        });

    }

    void getMoods(String accessToken) {
        apiService.getMoods(accessToken).enqueue(new Callback<MoodResponse>() {
            @Override
            public void onResponse(Call<MoodResponse> call, Response<MoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0 && response.body().getMoods().size() != 0) {
                        searchMusicListener.onGetMoods(response.body().getMoods());
                    } else {
                        searchMusicListener.onGetError(response.message());
                    }
                } else {
                    searchMusicListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MoodResponse> call, Throwable t) {
                searchMusicListener.onGetError(t.getMessage());
            }
        });
    }

    void getMusicByMood(String accessToken, ByMoodRequest byMoodRequest) {
        apiService.getMusicsByMood(accessToken, byMoodRequest).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0 && response.body().getAlbum().size() != 0) {
                        searchMusicListener.onGetMusic(response.body());
                    } else {
                        searchMusicListener.onGetError(response.message());
                    }
                } else {
                    searchMusicListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                searchMusicListener.onGetError(t.getMessage());
            }
        });
    }

    void searchMusic(String accessToken, SearchMusicRequest searchMusicRequest) {
        apiService.searchMusic(accessToken, searchMusicRequest).enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getAlbum().size() > 0)
                        searchMusicListener.onGetMusic(response.body());
                } else {
                    searchMusicListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {
                searchMusicListener.onGetError(t.getMessage());
            }
        });
    }
}