package com.travelguide.travelguide.ui.login.password;

import com.travelguide.travelguide.model.response.ForgotPasswordResponse;

public interface FPasswordListener {

    void onGetForgotPasswordResponse(ForgotPasswordResponse forgotPasswordResponse);

    void onGetForgotPasswordError(String message);

}
