package com.example.travelguide.ui.home.presenter;

import com.example.travelguide.model.request.ProfileRequest;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.response.ProfileResponse;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.home.interfaces.ICustomerProfileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProfilePresenter {
    private ICustomerProfileActivity iCustomerProfileActivity;
    private ApiService apiService;

    public CustomerProfilePresenter(ICustomerProfileActivity iCustomerProfileActivity) {
        this.iCustomerProfileActivity = iCustomerProfileActivity;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(accessToken, profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 1) {
                        iCustomerProfileActivity.onGetProfile(response.body());
                    } else {
                        iCustomerProfileActivity.onGerProfileError(response.message());
                    }
                } else {
                    iCustomerProfileActivity.onGerProfileError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                iCustomerProfileActivity.onGerProfileError(t.getMessage());
            }
        });
    }

    public void follow(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        iCustomerProfileActivity.onFollowSuccess(response.body());
                    } else {
                        iCustomerProfileActivity.onFollowError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                iCustomerProfileActivity.onFollowError(t.getMessage());

            }
        });
    }
}
