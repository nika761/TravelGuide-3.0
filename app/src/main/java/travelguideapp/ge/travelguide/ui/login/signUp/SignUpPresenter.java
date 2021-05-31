package travelguideapp.ge.travelguide.ui.login.signUp;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import travelguideapp.ge.travelguide.model.request.SignUpRequest;
import travelguideapp.ge.travelguide.model.request.CheckNickRequest;
import travelguideapp.ge.travelguide.model.response.SignUpResponse;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.network.api.AuthorizationApi;

class SignUpPresenter {
    private final SignUpListener signUpListener;
    private final AuthorizationApi authorizationApi;

    SignUpPresenter(SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
        this.authorizationApi = RetrofitManager.getAuthorizationApi();
    }

    void signUp(SignUpRequest signUpRequest) {
        authorizationApi.signUp(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignUpResponse> call, @NotNull Response<SignUpResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    signUpListener.onSignUpResponse(response.body());
                } else {
                    signUpListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SignUpResponse> call, @NotNull Throwable t) {
                signUpListener.onError(t.getMessage());
            }
        });
    }

//    public void checkEmail(CheckMailRequest checkMailRequest) {
//        service.checkEmail(checkMailRequest).enqueue(new Callback<CheckMailResponse>() {
//            @Override
//            public void onResponse(Call<CheckMailResponse> call, Response<CheckMailResponse> response) {
//                if (response.isSuccessful()) {
//                    iSignUpFragment.onGetEmailCheckResult(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CheckMailResponse> call, Throwable t) {
//
//            }
//        });
//    }



    void checkNick(CheckNickRequest checkNickRequest) {
        authorizationApi.checkNick(checkNickRequest).enqueue(new Callback<CheckNickResponse>() {
            @Override
            public void onResponse(@NotNull Call<CheckNickResponse> call, @NotNull Response<CheckNickResponse> response) {
                if (response.isSuccessful()) {
                    signUpListener.onGetNickCheckResult(response.body());
                } else {
                    signUpListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckNickResponse> call, @NotNull Throwable t) {
                signUpListener.onError(t.getMessage());
            }
        });
    }

    void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    signUpListener.onPhotoUploadToS3();
                } else if (TransferState.FAILED == state) {
                    signUpListener.onError("error");
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                signUpListener.onError(ex.getMessage());
            }
        });

    }
}
