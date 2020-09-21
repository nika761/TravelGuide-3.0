package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.User;
import com.example.travelguide.model.response.LoginResponse;

public interface ISignInFragment {

    void userFirstLogin(String key, int platform);

    void onSign(LoginResponse loginResponse);

    void onAuthError(String message);

    void onFireBaseAuthSignIn();

    void onFireBaseAuthSignUp();
}
