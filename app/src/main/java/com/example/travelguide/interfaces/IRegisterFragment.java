package com.example.travelguide.interfaces;

import com.example.travelguide.model.CheckMailResponseModel;
import com.example.travelguide.model.AuthResponseModel;

public interface IRegisterFragment {

    void onGetAuthResult(AuthResponseModel authResponseModel);

    void onGetEmailCheckResult(CheckMailResponseModel checkMailResponseModel);

}
