package com.travel.guide.ui.login.signUp;

import com.travel.guide.model.response.CheckNickResponse;
import com.travel.guide.model.response.SignUpWithFirebaseResponse;

public interface SignUpFireBaseListener {

    void onSuccess(SignUpWithFirebaseResponse signUpWithFirebaseResponse);

    void onError(String message);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

    void checkNickName();

}
