package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.User;
import com.example.travelguide.model.response.AppSettingsResponse;
import com.example.travelguide.model.response.AuthWithFirebaseResponse;
import com.example.travelguide.model.response.LoginResponse;

public interface ISignInFragment {

    void onSign(LoginResponse loginResponse);

    void onAuthError(String message);

    void onFireBaseAuthSignIn(AuthWithFirebaseResponse authWithFirebaseResponse);

    void onFireBaseSignUp(String token, int platform, String name);

    void onFireBaseAuthSignUp();

    void onGetSettings(AppSettingsResponse.App_settings appSettings);

    void onGetSettingsError(String message);
}
