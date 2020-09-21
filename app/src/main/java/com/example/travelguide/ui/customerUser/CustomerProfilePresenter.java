package com.example.travelguide.ui.customerUser;

import com.example.travelguide.model.request.ProfileRequest;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.response.ProfileResponse;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

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
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        customerProfileListener.onGetProfile(response.body());
                    } else {
                        customerProfileListener.onGerProfileError(response.message());
                    }
                } else {
                    customerProfileListener.onGerProfileError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                customerProfileListener.onGerProfileError(t.getMessage());
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
                        customerProfileListener.onFollowError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                customerProfileListener.onFollowError(t.getMessage());

            }
        });
    }
}
