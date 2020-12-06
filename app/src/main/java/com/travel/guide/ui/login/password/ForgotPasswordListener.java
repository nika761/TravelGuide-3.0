package com.travel.guide.ui.login.password;

import com.travel.guide.model.response.ChangePasswordResponse;
import com.travel.guide.model.response.ForgotPasswordResponse;

public interface ForgotPasswordListener {

    void onForgetPassword(ForgotPasswordResponse forgotPasswordResponse);

    void onChangePassword(ChangePasswordResponse changePasswordResponse);

    void onError(String message);

}
