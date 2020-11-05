package com.travel.guide.ui.home.profile.userFollowActivity.follower;

import com.travel.guide.model.request.FollowRequest;
import com.travel.guide.model.request.FollowersRequest;
import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.FollowerResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFragmentPresenter {
    private FollowersFragmentListener followerFragmentListener;
    private ApiService apiService;

    FollowersFragmentPresenter(FollowersFragmentListener followerFragmentListener) {
        this.followerFragmentListener = followerFragmentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getFollowers(String accessToken, FollowersRequest followersRequest) {
        apiService.getFollowers(accessToken, followersRequest).enqueue(new Callback<FollowerResponse>() {
            @Override
            public void onResponse(Call<FollowerResponse> call, Response<FollowerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        followerFragmentListener.onGetFollowers(response.body());
                    } else {
                        followerFragmentListener.onGetFollowersError(response.message());
                    }
                } else {
                    followerFragmentListener.onGetFollowersError(response.message());
                }
            }

            @Override
            public void onFailure(Call<FollowerResponse> call, Throwable t) {
                followerFragmentListener.onGetFollowersError(t.getMessage());

            }
        });

    }

    public void follow(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        followerFragmentListener.onFollowSuccess(response.body());
                    } else {
                        followerFragmentListener.onFollowError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                followerFragmentListener.onFollowError(t.getMessage());
            }
        });
    }
}
