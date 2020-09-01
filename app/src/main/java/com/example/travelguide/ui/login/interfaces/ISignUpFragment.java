package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.response.CheckMailResponse;
import com.example.travelguide.model.response.SignUpResponse;
import com.example.travelguide.model.response.CheckNickResponse;

public interface ISignUpFragment {

    void onGetAuthResult(SignUpResponse signUpResponse);

    void onGetAuthError(String message);

    void onGetEmailCheckResult(CheckMailResponse checkMailResponse);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

}
