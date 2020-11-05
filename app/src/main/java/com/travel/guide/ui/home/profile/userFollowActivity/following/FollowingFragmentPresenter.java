package com.travel.guide.ui.home.profile.userFollowActivity.following;

import com.travel.guide.model.request.FollowingRequest;
import com.travel.guide.model.response.FollowingResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FollowingFragmentPresenter {
    private FollowingFragmentListener iFollowingFragment;
    private ApiService apiService;

    FollowingFragmentPresenter(FollowingFragmentListener iFollowingFragment) {
        this.iFollowingFragment = iFollowingFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    void getFollowing(String accessToken, FollowingRequest followingRequest) {
        apiService.getFollowing(accessToken, followingRequest).enqueue(new Callback<FollowingResponse>() {
            @Override
            public void onResponse(Call<FollowingResponse> call, Response<FollowingResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        iFollowingFragment.onGetFollowing(response.body());
                    } else {
                        iFollowingFragment.onGetFollowingError(response.message());
                    }
                } else {
                    iFollowingFragment.onGetFollowingError(response.message());
                }
            }

            @Override
            public void onFailure(Call<FollowingResponse> call, Throwable t) {
                iFollowingFragment.onGetFollowingError(t.getMessage());
            }
        });

    }
}