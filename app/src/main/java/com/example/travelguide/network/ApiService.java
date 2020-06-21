package com.example.travelguide.network;

import com.example.travelguide.model.request.AuthRequestModel;
import com.example.travelguide.model.response.AuthResponseModel;
import com.example.travelguide.model.request.CheckMailRequestModel;
import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.request.CheckNickRequestModel;
import com.example.travelguide.model.response.CheckNickResponseModel;
import com.example.travelguide.model.response.LanguagesResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<AuthResponseModel> sendUser(@Body AuthRequestModel authRequestModel);

    @POST("check_email")
    Call<CheckMailResponseModel> checkEmail(@Body CheckMailRequestModel checkMailRequestModel);

    @POST("check_nick")
    Call<CheckNickResponseModel> checkNick(@Body CheckNickRequestModel checkNickRequestModel);

    @GET("get_languages")
    Call<LanguagesResponseModel> getLanguages();
}
