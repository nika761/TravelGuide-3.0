package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.response.ForgotPasswordResponse;

public interface IForgotPassowrd {
    void onGetForgotPasswordResponse(ForgotPasswordResponse forgotPasswordResponse);

    void onGetForgotPasswordError(String message);
}
