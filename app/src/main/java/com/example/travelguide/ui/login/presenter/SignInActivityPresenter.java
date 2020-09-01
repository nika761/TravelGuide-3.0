package com.example.travelguide.ui.login.presenter;

import com.example.travelguide.model.request.VerifyEmailRequest;
import com.example.travelguide.model.response.VerifyEmailResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.login.interfaces.OnVerify;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivityPresenter {

    private OnVerify onVerify;
    private ApiService apiService;

    public SignInActivityPresenter(OnVerify onVerify) {
        this.onVerify = onVerify;
        this.apiService = RetrofitManager.getApiService();
    }

    public void verify(String accessToken, VerifyEmailRequest verifyEmailRequest) {
        apiService.verifyEmail(accessToken, verifyEmailRequest).enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        switch (response.body().getStatus()) {

                            case 0:
                                onVerify.onVerify(response.body());
                                break;

                            case -100:
                                onVerify.onVerifyError("");
                                break;
                        }
                    }
                } else {
                    onVerify.onVerifyError(response.message());
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {
                onVerify.onVerifyError(t.getMessage());

            }
        });
    }
}
