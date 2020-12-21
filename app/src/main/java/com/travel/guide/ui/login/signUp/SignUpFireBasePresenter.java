package com.travel.guide.ui.login.signUp;

import com.travel.guide.model.request.CheckNickRequest;
import com.travel.guide.model.request.SignUpWithFirebaseRequest;
import com.travel.guide.model.response.CheckNickResponse;
import com.travel.guide.model.response.SignUpWithFirebaseResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SignUpFireBasePresenter {

    private SignUpFireBaseListener signUpFireBaseListener;
    private ApiService apiService;

    SignUpFireBasePresenter(SignUpFireBaseListener signUpFireBaseListener) {
        this.signUpFireBaseListener = signUpFireBaseListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void signUpWithFirebase(SignUpWithFirebaseRequest signUpWithFirebaseRequest) {
        apiService.signUpWithFirebase(signUpWithFirebaseRequest).enqueue(new Callback<SignUpWithFirebaseResponse>() {
            @Override
            public void onResponse(Call<SignUpWithFirebaseResponse> call, Response<SignUpWithFirebaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            signUpFireBaseListener.onSuccess(response.body());
                            break;
                        case 1:
                        case 2:
                            signUpFireBaseListener.onError(response.message());
                            break;
                        case 3:
                            signUpFireBaseListener.checkNickName();
                            break;
                    }
                } else {
                    try {
                        signUpFireBaseListener.onError(response.errorBody().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpWithFirebaseResponse> call, Throwable t) {
                signUpFireBaseListener.onError(t.getMessage());
            }
        });
    }

    void checkNick(CheckNickRequest checkNickRequest) {
        apiService.checkNick(checkNickRequest).enqueue(new Callback<CheckNickResponse>() {
            @Override
            public void onResponse(Call<CheckNickResponse> call, Response<CheckNickResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    signUpFireBaseListener.onGetNickCheckResult(response.body());
                } else {
                    signUpFireBaseListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CheckNickResponse> call, Throwable t) {
                signUpFireBaseListener.onError(t.getMessage());
            }
        });
    }
}
