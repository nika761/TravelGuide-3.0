package com.example.travelguide.interfaces;

import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.response.SignUpResponseModel;
import com.example.travelguide.model.response.CheckNickResponseModel;

public interface ISignUpFragment {

    void onGetAuthResult(SignUpResponseModel signUpResponseModel);

    void onGetEmailCheckResult(CheckMailResponseModel checkMailResponseModel);

    void onGetNickCheckResult(CheckNickResponseModel checkNickResponseModel);

}
