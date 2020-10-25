package com.example.travelguide.ui.login.password;

import com.example.travelguide.model.request.ForgotPasswordRequest;
import com.example.travelguide.model.response.ForgotPasswordResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FPasswordPresenter {
    private FPasswordListener iForgotPassowrd;
    private ApiService apiService;

    FPasswordPresenter(FPasswordListener iForgotPassowrd) {
        this.iForgotPassowrd = iForgotPassowrd;
        this.apiService = RetrofitManager.getApiService();
    }

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        apiService.forgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    iForgotPassowrd.onGetForgotPasswordResponse(response.body());
                } else {
                    iForgotPassowrd.onGetForgotPasswordError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                iForgotPassowrd.onGetForgotPasswordError(t.getMessage());
            }
        });
    }
}
