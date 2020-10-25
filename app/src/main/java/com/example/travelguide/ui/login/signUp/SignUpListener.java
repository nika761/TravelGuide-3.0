package com.example.travelguide.ui.login.signUp;

import com.example.travelguide.model.response.CheckMailResponse;
import com.example.travelguide.model.response.SignUpResponse;
import com.example.travelguide.model.response.CheckNickResponse;

public interface SignUpListener {

    void onSignUpResponse(SignUpResponse signUpResponse);

    void onError(String message);

    void onPhotoUploadSuccess();

//    void onGetEmailCheckResult(CheckMailResponse checkMailResponse);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

}
