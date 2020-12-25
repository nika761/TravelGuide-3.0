package travelguideapp.ge.travelguide.ui.login.password;

import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.model.response.ForgotPasswordResponse;

public interface ForgotPasswordListener {

    void onForgetPassword(ForgotPasswordResponse forgotPasswordResponse);

    void onChangePassword(ChangePasswordResponse changePasswordResponse);

    void onError(String message);

}
