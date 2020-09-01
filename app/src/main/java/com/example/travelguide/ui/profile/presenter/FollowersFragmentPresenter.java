package com.example.travelguide.ui.profile.presenter;

import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.FollowersRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.FollowerResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.profile.interfaces.IFollowerFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFragmentPresenter {
    private IFollowerFragment iFollowerFragment;
    private ApiService apiService;

    public FollowersFragmentPresenter(IFollowerFragment iFollowerFragment) {
        this.iFollowerFragment = iFollowerFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getFollowers(String accessToken, FollowersRequest followersRequest) {
        apiService.getFollowers(accessToken, followersRequest).enqueue(new Callback<FollowerResponse>() {
            @Override
            public void onResponse(Call<FollowerResponse> call, Response<FollowerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        iFollowerFragment.onGetFollowers(response.body());
                    } else {
                        iFollowerFragment.onGetFollowersError(response.message());
                    }
                } else {
                    iFollowerFragment.onGetFollowersError(response.message());
                }
            }

            @Override
            public void onFailure(Call<FollowerResponse> call, Throwable t) {
                iFollowerFragment.onGetFollowersError(t.getMessage());

            }
        });

    }

    public void follow(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        iFollowerFragment.onFollowSuccess(response.body());
                    } else {
                        iFollowerFragment.onFollowError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                iFollowerFragment.onFollowError(t.getMessage());
            }
        });
    }
}
