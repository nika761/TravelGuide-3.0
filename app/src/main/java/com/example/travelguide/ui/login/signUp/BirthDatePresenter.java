package com.example.travelguide.ui.login.signUp;

import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.request.SignUpWithFirebaseRequest;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.SignUpWithFirebaseResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class BirthDatePresenter {

    private BirthDateListener birthDateListener;
    private ApiService apiService;

    BirthDatePresenter(BirthDateListener birthDateListener) {
        this.birthDateListener = birthDateListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void signUpWithFirebase(SignUpWithFirebaseRequest signUpWithFirebaseRequest) {
        apiService.signUpWithFirebase(signUpWithFirebaseRequest).enqueue(new Callback<SignUpWithFirebaseResponse>() {
            @Override
            public void onResponse(Call<SignUpWithFirebaseResponse> call, Response<SignUpWithFirebaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            birthDateListener.onSuccess(response.body());
                            break;
                        case 1:
                        case 2:
                            birthDateListener.onError(response.message());
                            break;
                        case 3:
                            birthDateListener.checkNickName();
                            break;
                    }
                } else {
                    birthDateListener.onError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SignUpWithFirebaseResponse> call, Throwable t) {
                birthDateListener.onError(t.getMessage());
            }
        });
    }

    void checkNick(CheckNickRequest checkNickRequest) {
        apiService.checkNick(checkNickRequest).enqueue(new Callback<CheckNickResponse>() {
            @Override
            public void onResponse(Call<CheckNickResponse> call, Response<CheckNickResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    birthDateListener.onGetNickCheckResult(response.body());
                } else {
                    birthDateListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CheckNickResponse> call, Throwable t) {
                birthDateListener.onError(t.getMessage());
            }
        });
    }
}
