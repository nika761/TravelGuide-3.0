package com.example.travelguide.ui.login.password;

import com.example.travelguide.model.response.ResetPasswordResponse;

public interface RPasswordListener {

    void onPasswordReset(ResetPasswordResponse resetPasswordResponse);

    void onPasswordResetError(String message);

}
