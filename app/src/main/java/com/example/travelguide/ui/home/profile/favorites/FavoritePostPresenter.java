package com.example.travelguide.ui.home.profile.favorites;

import com.example.travelguide.model.request.FavoritePostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FavoritePostPresenter {
    private FavoritePostListener favoritePostListener;
    private ApiService apiService;

    FavoritePostPresenter(FavoritePostListener favoritePostListener) {
        this.favoritePostListener = favoritePostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getFavoritePosts(String accessToken, FavoritePostRequest favoritePostRequest) {
        apiService.getFavoritePosts(accessToken, favoritePostRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    favoritePostListener.onGetPosts(response.body());
                } else {
                    favoritePostListener.onGetPostsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                favoritePostListener.onGetPostsError(t.getMessage());
            }
        });
    }
}
