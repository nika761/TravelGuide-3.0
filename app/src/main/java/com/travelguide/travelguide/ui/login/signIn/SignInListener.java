package com.travelguide.travelguide.ui.login.signIn;

import com.travelguide.travelguide.model.response.AppSettingsResponse;
import com.travelguide.travelguide.model.response.AuthWithFirebaseResponse;
import com.travelguide.travelguide.model.response.LoginResponse;
import com.travelguide.travelguide.model.response.VerifyEmailResponse;

public interface SignInListener {

    void onVerify(VerifyEmailResponse verifyEmailResponse);

    void onSign(LoginResponse loginResponse);

    void onError(String message);

    void onFireBaseAuthSignIn(AuthWithFirebaseResponse authWithFirebaseResponse);

    void onFireBaseSignUp(String token, int platform, String name);

    void onFireBaseAuthSignUp();

    void onGetSettings(AppSettingsResponse.App_settings appSettings);

}
