package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.SignUpWithFirebaseResponse;

public interface IDateListener {

    void onSuccess(SignUpWithFirebaseResponse signUpWithFirebaseResponse);

    void onError(String message);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

    void checkNickName();

}
