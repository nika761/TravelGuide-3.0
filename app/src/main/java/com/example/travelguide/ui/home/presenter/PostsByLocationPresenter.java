package com.example.travelguide.ui.home.presenter;

import com.example.travelguide.model.request.PostByLocationRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.home.interfaces.IPostByLocationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsByLocationPresenter {
    private IPostByLocationActivity iPostByLocationActivity;
    private ApiService apiService;

    public PostsByLocationPresenter(IPostByLocationActivity iPostByLocationActivity) {
        this.iPostByLocationActivity = iPostByLocationActivity;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getPostsByLocation(String accessToken, PostByLocationRequest postByLocationRequest) {
        apiService.getPostsByLocation(accessToken, postByLocationRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0)
                        iPostByLocationActivity.onGetPosts(response.body());
                    else
                        iPostByLocationActivity.onGetPostError(response.message());
                } else {
                    iPostByLocationActivity.onGetPostError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                iPostByLocationActivity.onGetPostError(t.getMessage());
            }
        });
    }
}
