package com.example.travelguide.interfaces;

import com.example.travelguide.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface ISignInFragment {

    void onGetFbUserData(User user);

    void onGetGglUserData(User user);

}
