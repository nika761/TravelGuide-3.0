package com.travelguide.travelguide.ui.login.signUp;

import com.travelguide.travelguide.model.response.CheckNickResponse;
import com.travelguide.travelguide.model.response.SignUpWithFirebaseResponse;

public interface SignUpFireBaseListener {

    void onSuccess(SignUpWithFirebaseResponse signUpWithFirebaseResponse);

    void onError(String message);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

    void checkNickName();

}
