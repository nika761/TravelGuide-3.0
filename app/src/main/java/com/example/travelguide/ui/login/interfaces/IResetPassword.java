package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.response.ResetPasswordResponse;

public interface IResetPassword {

    void onPasswordReset(ResetPasswordResponse resetPasswordResponse);

    void onPasswordResetError(String message);

}
