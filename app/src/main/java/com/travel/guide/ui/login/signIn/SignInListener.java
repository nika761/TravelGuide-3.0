package com.travel.guide.ui.login.signIn;

import com.travel.guide.model.response.AppSettingsResponse;
import com.travel.guide.model.response.AuthWithFirebaseResponse;
import com.travel.guide.model.response.LoginResponse;
import com.travel.guide.model.response.VerifyEmailResponse;

public interface SignInListener {

    void onVerify(VerifyEmailResponse verifyEmailResponse);

    void onSign(LoginResponse loginResponse);

    void onError(String message);

    void onFireBaseAuthSignIn(AuthWithFirebaseResponse authWithFirebaseResponse);

    void onFireBaseSignUp(String token, int platform, String name);

    void onFireBaseAuthSignUp();

}
