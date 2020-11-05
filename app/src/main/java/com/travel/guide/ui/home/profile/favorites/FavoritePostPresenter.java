package com.travel.guide.ui.home.profile.favorites;

import com.travel.guide.model.request.FavoritePostRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

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
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        favoritePostListener.onGetPosts(response.body());
                    else
                        favoritePostListener.onGetPostsError(response.message() + response.body().getStatus());
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