package com.travel.guide.ui.login.signUp;

import com.travel.guide.model.response.SignUpResponse;
import com.travel.guide.model.response.CheckNickResponse;

public interface SignUpListener {

    void onSignUpResponse(SignUpResponse signUpResponse);

    void onError(String message);

    void onPhotoUploadSuccess();

//    void onGetEmailCheckResult(CheckMailResponse checkMailResponse);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

}
