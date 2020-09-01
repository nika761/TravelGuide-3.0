package com.example.travelguide.ui.profile.presenter;

import com.example.travelguide.model.request.FollowingRequest;
import com.example.travelguide.model.response.FollowingResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.profile.interfaces.IFollowingFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingFragmentPresenter {
    private IFollowingFragment iFollowingFragment;
    private ApiService apiService;

    public FollowingFragmentPresenter(IFollowingFragment iFollowingFragment) {
        this.iFollowingFragment = iFollowingFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getFollowing(String accessToken, FollowingRequest followingRequest) {
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
