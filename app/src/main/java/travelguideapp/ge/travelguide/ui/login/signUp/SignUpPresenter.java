package travelguideapp.ge.travelguide.ui.login.signUp;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.model.request.SignUpRequest;
import travelguideapp.ge.travelguide.model.request.CheckNickRequest;
import travelguideapp.ge.travelguide.model.response.SignUpResponse;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.network.api.AuthorizationApi;
import travelguideapp.ge.travelguide.ui.home.HomePageListener;
import travelguideapp.ge.travelguide.ui.home.HomePagePresenter;

class SignUpPresenter extends BasePresenter<SignUpListener> {

    private final AuthorizationApi authorizationApi;

    private SignUpPresenter(SignUpListener signUpListener) {
        super.attachView(signUpListener);
        this.authorizationApi = RetrofitManager.getAuthorizationApi();
    }

    public static SignUpPresenter with(SignUpListener signUpListener) {
        return new SignUpPresenter(signUpListener);
    }

    void signUp(SignUpRequest signUpRequest) {
        super.showLoader();
        authorizationApi.signUp(signUpRequest).enqueue(new BaseCallback<SignUpResponse>(this) {
            @Override
            protected void onSuccess(Response<SignUpResponse> response) {
                if (isViewAttached())
                    listener.onSignUpResponse(response.body());
            }
        });
    }


    void checkNick(CheckNickRequest checkNickRequest) {
        super.showLoader();
        authorizationApi.checkNick(checkNickRequest).enqueue(new BaseCallback<CheckNickResponse>(this) {
            @Override
            protected void onSuccess(Response<CheckNickResponse> response) {
                if (isViewAttached())
                    listener.onGetNickCheckResult(response.body());
            }
        });
    }

    void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    listener.onPhotoUploadToS3();
                } else if (TransferState.FAILED == state) {
                    listener.onError("error");
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                listener.onError(ex.getMessage());
            }
        });

    }


}
