package com.travel.guide.ui.login.password;

import com.travel.guide.model.response.ResetPasswordResponse;

public interface RPasswordListener {

    void onPasswordReset(ResetPasswordResponse resetPasswordResponse);

    void onPasswordResetError(String message);

}
