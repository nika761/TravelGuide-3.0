package com.example.travelguide.presenters;

import com.example.travelguide.interfaces.IRegisterFragment;
import com.example.travelguide.model.AuthRequestModel;
import com.example.travelguide.model.AuthResponseModel;
import com.example.travelguide.model.CheckMailRequestModel;
import com.example.travelguide.model.CheckMailResponseModel;
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
            }
        });
    }

    public void checkEmail(CheckMailRequestModel checkMailRequestModel) {
        service.checkEmail(checkMailRequestModel).enqueue(new Callback<CheckMailResponseModel>() {
            @Override
            public void onResponse(Call<CheckMailResponseModel> call, Response<CheckMailResponseModel> response) {
                iRegisterFragment.onGetEmailCheckResult(response.body());
            }

            @Override
            public void onFailure(Call<CheckMailResponseModel> call, Throwable t) {

            }
        });
    }
}
