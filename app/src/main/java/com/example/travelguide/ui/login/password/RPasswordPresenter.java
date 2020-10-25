package com.example.travelguide.ui.login.password;

import com.example.travelguide.model.request.ResetPasswordRequest;
import com.example.travelguide.model.response.ResetPasswordResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RPasswordPresenter {
    private RPasswordListener iResetPassword;
    private ApiService apiService;

    RPasswordPresenter(RPasswordListener iResetPassword) {
        this.iResetPassword = iResetPassword;
        this.apiService = RetrofitManager.getApiService();
    }

    void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        apiService.resetPassword(resetPasswordRequest).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    iResetPassword.onPasswordReset(response.body());
                } else {
                    iResetPassword.onPasswordResetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                iResetPassword.onPasswordResetError(t.getMessage());
            }
        });
    }

}
