package com.travel.guide.ui.login.password;

import com.travel.guide.model.response.ForgotPasswordResponse;

public interface FPasswordListener {

    void onGetForgotPasswordResponse(ForgotPasswordResponse forgotPasswordResponse);

    void onGetForgotPasswordError(String message);

}
