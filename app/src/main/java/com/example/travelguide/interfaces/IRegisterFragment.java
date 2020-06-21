package com.example.travelguide.interfaces;

import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.response.AuthResponseModel;
import com.example.travelguide.model.response.CheckNickResponseModel;

public interface IRegisterFragment {

    void onGetAuthResult(AuthResponseModel authResponseModel);

    void onGetEmailCheckResult(CheckMailResponseModel checkMailResponseModel);

    void onGetNickCheckResult(CheckNickResponseModel checkNickResponseModel);

}
