package com.example.travelguide.ui.login.password;

import com.example.travelguide.model.response.ForgotPasswordResponse;

public interface FPasswordListener {

    void onGetForgotPasswordResponse(ForgotPasswordResponse forgotPasswordResponse);

    void onGetForgotPasswordError(String message);

}
