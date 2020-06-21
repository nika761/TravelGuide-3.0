package com.example.travelguide.presenters;

import com.example.travelguide.interfaces.IRegisterFragment;
import com.example.travelguide.model.request.AuthRequestModel;
import com.example.travelguide.model.request.CheckNickRequestModel;
import com.example.travelguide.model.response.AuthResponseModel;
import com.example.travelguide.model.request.CheckMailRequestModel;
import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.response.CheckNickResponseModel;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    private IRegisterFragment iRegisterFragment;
    private ApiService service;

    public RegisterPresenter(IRegisterFragment iRegisterFragment) {
        this.iRegisterFragment = iRegisterFragment;
        service = RetrofitManager.getApiservice();
    }

    public void sendAuthResponse(AuthRequestModel authRequestModel) {
        service.sendUser(authRequestModel).enqueue(new Callback<AuthResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<AuthResponseModel> call, @NotNull Response<AuthResponseModel> response) {
//                String token = response.body().getAccess_token();
                if (response.isSuccessful()) {
                    iRegisterFragment.onGetAuthResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void checkEmail(CheckMailRequestModel checkMailRequestModel) {
        service.checkEmail(checkMailRequestModel).enqueue(new Callback<CheckMailResponseModel>() {
            @Override
            public void onResponse(Call<CheckMailResponseModel> call, Response<CheckMailResponseModel> response) {
                if (response.isSuccessful()) {
                    iRegisterFragment.onGetEmailCheckResult(response.body());
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
                    iRegisterFragment.onGetNickCheckResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<CheckNickResponseModel> call, Throwable t) {

            }
        });
    }
}
