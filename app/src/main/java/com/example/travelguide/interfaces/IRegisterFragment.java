package com.example.travelguide.interfaces;

import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.response.RegisterResponseModel;
import com.example.travelguide.model.response.CheckNickResponseModel;

public interface IRegisterFragment {

    void onGetAuthResult(RegisterResponseModel registerResponseModel);

    void onGetEmailCheckResult(CheckMailResponseModel checkMailResponseModel);

    void onGetNickCheckResult(CheckNickResponseModel checkNickResponseModel);

}
