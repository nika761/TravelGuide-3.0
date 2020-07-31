package com.example.travelguide.ui.login.presenter;

import com.example.travelguide.ui.login.interfaces.ISignUpFragment;
import com.example.travelguide.model.request.SignUpRequestModel;
import com.example.travelguide.model.request.CheckNickRequestModel;
import com.example.travelguide.model.response.SignUpResponseModel;
import com.example.travelguide.model.request.CheckMailRequestModel;
import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.response.CheckNickResponseModel;
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

    public void sendAuthResponse(SignUpRequestModel signUpRequestModel) {
        service.signUp(signUpRequestModel).enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<SignUpResponseModel> call, @NotNull Response<SignUpResponseModel> response) {
//                String token = response.body().getAccess_token();
                if (response.isSuccessful()) {
                    iSignUpFragment.onGetAuthResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void checkEmail(CheckMailRequestModel checkMailRequestModel) {
        service.checkEmail(checkMailRequestModel).enqueue(new Callback<CheckMailResponseModel>() {
            @Override
            public void onResponse(Call<CheckMailResponseModel> call, Response<CheckMailResponseModel> response) {
                if (response.isSuccessful()) {
                    iSignUpFragment.onGetEmailCheckResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<CheckMailResponseModel> call, Throwable t) {

            }
        });
    }

    public void checkNick(CheckNickRequestModel checkNickRequestModel) {
        service.checkNick(checkNickRequestModel).enqueue(new Callback<CheckNickResponseModel>() {
            @Override
            public void onResponse(Call<CheckNickResponseModel> call, Response<CheckNickResponseModel> response) {
                if (response.isSuccessful()) {
                    iSignUpFragment.onGetNickCheckResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<CheckNickResponseModel> call, Throwable t) {

            }
        });
    }
}
