package com.example.travelguide.ui.login.signUp;

import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.SignUpWithFirebaseResponse;

public interface BirthDateListener {

    void onSuccess(SignUpWithFirebaseResponse signUpWithFirebaseResponse);

    void onError(String message);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

    void checkNickName();

}
