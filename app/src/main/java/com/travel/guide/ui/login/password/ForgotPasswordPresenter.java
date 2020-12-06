package com.travel.guide.ui.login.password;

import com.travel.guide.model.request.ChangePasswordRequest;
import com.travel.guide.model.request.ForgotPasswordRequest;
import com.travel.guide.model.response.ChangePasswordResponse;
import com.travel.guide.model.response.ForgotPasswordResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ForgotPasswordPresenter {
    private ForgotPasswordListener listener;
    private ApiService apiService;

    ForgotPasswordPresenter(ForgotPasswordListener iForgotPassowrd) {
        this.listener = iForgotPassowrd;
        this.apiService = RetrofitManager.getApiService();
    }

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        apiService.forgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onForgetPassword(response.body());
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    void changePassword(String accessToken, ChangePasswordRequest changePasswordRequest) {
        apiService.changePassword(accessToken, changePasswordRequest).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onChangePassword(response.body());
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
