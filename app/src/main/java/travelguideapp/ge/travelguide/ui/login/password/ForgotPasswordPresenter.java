package travelguideapp.ge.travelguide.ui.login.password;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.request.ForgotPasswordRequest;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.model.response.ForgotPasswordResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.network.api.AuthorizationApi;

class ForgotPasswordPresenter {
    private ForgotPasswordListener listener;
    private AuthorizationApi authorizationApi;

    ForgotPasswordPresenter(ForgotPasswordListener iForgotPassowrd) {
        this.listener = iForgotPassowrd;
        this.authorizationApi = RetrofitManager.getAuthorizationApi();
    }

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        authorizationApi.forgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(@NotNull Call<ForgotPasswordResponse> call, @NotNull Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onForgetPassword(response.body());
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ForgotPasswordResponse> call, @NotNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

}
