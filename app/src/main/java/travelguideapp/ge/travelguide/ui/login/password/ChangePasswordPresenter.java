package travelguideapp.ge.travelguide.ui.login.password;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.network.RetrofitManager;
import travelguideapp.ge.travelguide.network.api.AuthorizationApi;
import travelguideapp.ge.travelguide.network.ErrorHandler;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordListener> {

    private final AuthorizationApi authorizationApi;

    private ChangePasswordPresenter(ChangePasswordListener changePasswordListener) {
        super.attachView(changePasswordListener);
        this.authorizationApi = RetrofitManager.getAuthorizationApi();
    }

    public static ChangePasswordPresenter with(ChangePasswordListener changePasswordListener) {
        return new ChangePasswordPresenter(changePasswordListener);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        super.showLoader();
        authorizationApi.changePassword(changePasswordRequest).enqueue(new BaseCallback<ChangePasswordResponse>(this) {
            @Override
            protected void onSuccess(Response<ChangePasswordResponse> response) {
                if (isViewAttached()) {
                    listener.onChangePasswordResponse(response.body());
                }
            }
        });

    }

}
