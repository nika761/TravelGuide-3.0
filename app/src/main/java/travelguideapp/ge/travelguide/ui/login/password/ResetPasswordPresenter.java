package travelguideapp.ge.travelguide.ui.login.password;

import travelguideapp.ge.travelguide.model.request.ResetPasswordRequest;
import travelguideapp.ge.travelguide.model.response.ResetPasswordResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ResetPasswordPresenter {
    private ResetPasswordListener iResetPassword;
    private ApiService apiService;

    ResetPasswordPresenter(ResetPasswordListener iResetPassword) {
        this.iResetPassword = iResetPassword;
        this.apiService = RetrofitManager.getApiService();
    }

    void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        apiService.resetPassword(resetPasswordRequest).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    iResetPassword.onPasswordReset(response.body());
                } else {
                    iResetPassword.onPasswordResetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                iResetPassword.onPasswordResetError(t.getMessage());
            }
        });
    }

}
