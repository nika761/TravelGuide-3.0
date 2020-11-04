package com.travelguide.travelguide.ui.login.password;

import com.travelguide.travelguide.model.response.ResetPasswordResponse;

public interface RPasswordListener {

    void onPasswordReset(ResetPasswordResponse resetPasswordResponse);

    void onPasswordResetError(String message);

}
