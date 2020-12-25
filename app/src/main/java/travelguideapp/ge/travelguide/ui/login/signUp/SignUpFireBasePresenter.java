package travelguideapp.ge.travelguide.ui.login.signUp;

import travelguideapp.ge.travelguide.model.request.CheckNickRequest;
import travelguideapp.ge.travelguide.model.request.SignUpWithFirebaseRequest;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.model.response.SignUpWithFirebaseResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

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