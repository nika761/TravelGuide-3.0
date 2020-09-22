package com.example.travelguide.ui.login.presenter;

import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.request.SignUpWithFirebaseRequest;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.SignUpWithFirebaseResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.login.interfaces.IDateListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatePresenter {

    private IDateListener iDateListener;
    private ApiService apiService;

    public DatePresenter(IDateListener iDateListener) {
        this.iDateListener = iDateListener;
        this.apiService = RetrofitManager.getApiService();
    }

    public void signUpWithFirebase(SignUpWithFirebaseRequest signUpWithFirebaseRequest) {
        apiService.signUpWithFirebase(signUpWithFirebaseRequest).enqueue(new Callback<SignUpWithFirebaseResponse>() {
            @Override
            public void onResponse(Call<SignUpWithFirebaseResponse> call, Response<SignUpWithFirebaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            iDateListener.onSuccess(response.body());
                            break;
                        case 1:
                        case 2:
                            iDateListener.onError(response.message());
                            break;
                        case 3:
                            iDateListener.checkNickName();
                            break;
                    }
                } else {
                    iDateListener.onError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SignUpWithFirebaseResponse> call, Throwable t) {
                iDateListener.onError(t.getMessage());
            }
        });
    }

    public void checkNick(CheckNickRequest checkNickRequest) {
        apiService.checkNick(checkNickRequest).enqueue(new Callback<CheckNickResponse>() {
            @Override
            public void onResponse(Call<CheckNickResponse> call, Response<CheckNickResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    iDateListener.onGetNickCheckResult(response.body());
                } else {
                    iDateListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CheckNickResponse> call, Throwable t) {
                iDateListener.onError(t.getMessage());
            }
        });
    }
}
