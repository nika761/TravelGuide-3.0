package com.example.travelguide.ui.login.signIn;

import com.example.travelguide.model.response.AppSettingsResponse;
import com.example.travelguide.model.response.AuthWithFirebaseResponse;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.model.response.VerifyEmailResponse;

public interface SignInListener {

    void onVerify(VerifyEmailResponse verifyEmailResponse);

    void onSign(LoginResponse loginResponse);

    void onError(String message);

    void onFireBaseAuthSignIn(AuthWithFirebaseResponse authWithFirebaseResponse);

    void onFireBaseSignUp(String token, int platform, String name);

    void onFireBaseAuthSignUp();

    void onGetSettings(AppSettingsResponse.App_settings appSettings);

}
