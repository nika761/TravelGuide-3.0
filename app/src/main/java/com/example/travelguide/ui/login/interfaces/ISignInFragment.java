package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.User;
import com.example.travelguide.model.response.LoginResponse;

public interface ISignInFragment {

    void onGetFbUserData(User user);

    void onGetGglUserData(User user);

    void onGetLoginResult(LoginResponse loginResponse);

}
