package travelguideapp.ge.travelguide.ui.login.password;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.base.BaseResponse;
import travelguideapp.ge.travelguide.utility.ResponseHandler;
import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.network.RetrofitManager;
import travelguideapp.ge.travelguide.network.api.AuthorizationApi;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordListener> {

    private ChangePasswordListener listener;
    private AuthorizationApi authorizationApi;

    public static ChangePasswordPresenter getInstance() {
        return new ChangePasswordPresenter();
    }

    private ChangePasswordPresenter() {
    }

    @Override
    protected void attachView(ChangePasswordListener changePasswordListener) {
        this.listener = changePasswordListener;
        this.authorizationApi = RetrofitManager.getAuthorizationApi();
    }

    @Override
    protected void detachView() {
        try {
            this.listener = null;
            this.authorizationApi = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        if (isViewAttached(listener))
            listener.showLoader();

        authorizationApi.changePassword(changePasswordRequest).enqueue(new BaseResponse<ChangePasswordResponse>() {
            @Override
            protected void onSuccess(Response<ChangePasswordResponse> response) {
                if (isViewAttached(listener)) {
                    listener.hideLoader();
                    listener.onChangePasswordResponse(response.body());
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, listener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, listener);
            }
        });

    }

}
