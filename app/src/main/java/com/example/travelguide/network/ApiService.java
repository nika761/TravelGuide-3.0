package com.example.travelguide.network;

import com.example.travelguide.model.AuthRequestModel;
import com.example.travelguide.model.AuthResponseModel;
import com.example.travelguide.model.CheckMailRequestModel;
import com.example.travelguide.model.CheckMailResponseModel;
import com.example.travelguide.model.LanguagesResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<AuthResponseModel> sendUser(@Body AuthRequestModel authRequestModel);

    @POST("check_email")
    Call<CheckMailResponseModel> checkEmail(@Body CheckMailRequestModel checkMailRequestModel);

    @GET("get_languages")
    Call<LanguagesResponseModel>getLanguages();
}
