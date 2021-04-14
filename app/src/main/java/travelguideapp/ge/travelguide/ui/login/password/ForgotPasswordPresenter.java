package travelguideapp.ge.travelguide.ui.login.password;

import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.request.ForgotPasswordRequest;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.model.response.ForgotPasswordResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ForgotPasswordPresenter {
    private ForgotPasswordListener listener;
    private ApiService apiService;

    ForgotPasswordPresenter(ForgotPasswordListener iForgotPassowrd) {
        this.listener = iForgotPassowrd;
        this.apiService = RetrofitManager.getApiService();
    }

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        apiService.forgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onForgetPassword(response.body());
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    void changePassword( ChangePasswordRequest changePasswordRequest) {
        apiService.changePassword(changePasswordRequest).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onChangePassword(response.body());
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
