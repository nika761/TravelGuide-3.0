package travelguideapp.ge.travelguide.ui.login.password;

import travelguideapp.ge.travelguide.model.response.ResetPasswordResponse;

public interface ResetPasswordListener {

    void onPasswordReset(ResetPasswordResponse resetPasswordResponse);

    void onPasswordResetError(String message);

}
