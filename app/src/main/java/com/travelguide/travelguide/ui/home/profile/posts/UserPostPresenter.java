package com.travelguide.travelguide.ui.home.profile.posts;

import com.travelguide.travelguide.model.request.PostByUserRequest;
import com.travelguide.travelguide.model.response.PostResponse;
import com.travelguide.travelguide.network.ApiService;
import com.travelguide.travelguide.network.RetrofitManager;

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
        apiService.getCustomerPosts(accessToken, postByUserRequest).enqueue(new Callback<PostResponse>() {
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
