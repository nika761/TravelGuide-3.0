package com.example.travelguide.ui.login.presenter;

import com.example.travelguide.ui.login.interfaces.ISignUpFragment;
import com.example.travelguide.model.request.SignUpRequest;
import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.response.SignUpResponse;
import com.example.travelguide.model.request.CheckMailRequest;
import com.example.travelguide.model.response.CheckMailResponse;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter {
    private ISignUpFragment iSignUpFragment;
    private ApiService service;

    public SignUpPresenter(ISignUpFragment iSignUpFragment) {
        this.iSignUpFragment = iSignUpFragment;
        service = RetrofitManager.getApiService();
    }

    public void sendAuthResponse(SignUpRequest signUpRequest) {
        service.signUp(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignUpResponse> call, @NotNull Response<SignUpResponse> response) {
//                String token = response.body().getAccess_token();
                if (response.isSuccessful()) {
                    iSignUpFragment.onGetAuthResult(response.body());
                } else {
                    iSignUpFragment.onGetAuthError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                iSignUpFragment.onGetAuthError(t.getMessage());
            }
        });
    }

    public void checkEmail(CheckMailRequest checkMailRequest) {
        service.checkEmail(checkMailRequest).enqueue(new Callback<CheckMailResponse>() {
            @Override
            public void onResponse(Call<CheckMailResponse> call, Response<CheckMailResponse> response) {
                if (response.isSuccessful()) {
                    iSignUpFragment.onGetEmailCheckResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<CheckMailResponse> call, Throwable t) {

            }
        });
    }

    public void checkNick(CheckNickRequest checkNickRequest) {
        service.checkNick(checkNickRequest).enqueue(new Callback<CheckNickResponse>() {
            @Override
            public void onResponse(Call<CheckNickResponse> call, Response<CheckNickResponse> response) {
                if (response.isSuccessful()) {
                    iSignUpFragment.onGetNickCheckResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<CheckNickResponse> call, Throwable t) {

            }
        });
    }
}
