package com.travel.guide.ui.customerUser;

import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.request.FollowRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProfilePresenter {
    private CustomerProfileListener customerProfileListener;
    private ApiService apiService;

    CustomerProfilePresenter(CustomerProfileListener customerProfileListener) {
        this.customerProfileListener = customerProfileListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(accessToken, profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        customerProfileListener.onGetProfile(response.body());
                    } else {
                        customerProfileListener.onError(response.body().getStatus() + response.message());
                    }
                } else {
                    customerProfileListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                customerProfileListener.onError(t.getMessage());
            }
        });
    }

    public void follow(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        customerProfileListener.onFollowSuccess(response.body());
                    } else {
                        customerProfileListener.onError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                customerProfileListener.onError(t.getMessage());

            }
        });
    }
}