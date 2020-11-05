package com.travel.guide.ui.home.profile.posts;

import com.travel.guide.model.request.PostByUserRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class UserPostPresenter {

    private UserPostListener userPostListener;
    private ApiService apiService;

    UserPostPresenter(UserPostListener userPostListener) {
        this.userPostListener = userPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getUserPosts(String accessToken, PostByUserRequest postByUserRequest) {
        apiService.getPostsByUser(accessToken, postByUserRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        userPostListener.onGetPosts(response.body());
                    } else {
                        userPostListener.onGetPostsError(response.message());
                    }
                } else {
                    userPostListener.onGetPostsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                userPostListener.onGetPostsError(t.getMessage());
            }
        });
    }
}
