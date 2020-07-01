package com.example.travelguide.interfaces;

import com.example.travelguide.model.User;
import com.example.travelguide.model.response.LoginResponseModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface ISignInFragment {

    void onGetFbUserData(User user);

    void onGetGglUserData(User user);

    void onGetLoginResult(LoginResponseModel loginResponseModel);

}
