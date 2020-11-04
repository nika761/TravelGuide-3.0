package com.travelguide.travelguide.ui.login.signUp;

import com.travelguide.travelguide.model.response.SignUpResponse;
import com.travelguide.travelguide.model.response.CheckNickResponse;

public interface SignUpListener {

    void onSignUpResponse(SignUpResponse signUpResponse);

    void onError(String message);

    void onPhotoUploadSuccess();

//    void onGetEmailCheckResult(CheckMailResponse checkMailResponse);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

}
