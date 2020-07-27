package com.example.travelguide.network;

import com.example.travelguide.model.request.AboutRequestModel;
import com.example.travelguide.model.request.ChangeLangRequestModel;
import com.example.travelguide.model.request.PostRequestModel;
import com.example.travelguide.model.request.SignUpRequestModel;
import com.example.travelguide.model.request.LoginRequestModel;
import com.example.travelguide.model.request.TermsPolicyRequestModel;
import com.example.travelguide.model.request.UploadStoryRequestModel;
import com.example.travelguide.model.response.AboutResponseModel;
import com.example.travelguide.model.response.ChangeLangResponseModel;
import com.example.travelguide.model.response.PostResponseModel;
import com.example.travelguide.model.response.SignUpResponseModel;
import com.example.travelguide.model.request.CheckMailRequestModel;
import com.example.travelguide.model.response.CheckMailResponseModel;
import com.example.travelguide.model.request.CheckNickRequestModel;
import com.example.travelguide.model.response.CheckNickResponseModel;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.model.response.TermsPolicyResponseModel;
import com.example.travelguide.model.response.UploadStoryResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<SignUpResponseModel> signUp(@Body SignUpRequestModel signUpRequestModel);

    @POST("check_email")
    Call<CheckMailResponseModel> checkEmail(@Body CheckMailRequestModel checkMailRequestModel);

    @POST("check_nick")
    Call<CheckNickResponseModel> checkNick(@Body CheckNickRequestModel checkNickRequestModel);

    @GET("get_languages")
    Call<LanguagesResponseModel> getLanguages();

    @POST("get_terms_policy")
    Call<TermsPolicyResponseModel> getTerms(@Body TermsPolicyRequestModel termsPolicyRequestModel);

    @POST("login")
    Call<LoginResponseModel> signIn(@Body LoginRequestModel loginRequestModel);

    @POST("get_about")
    Call<AboutResponseModel> getAbout(@Body AboutRequestModel aboutRequestModel);

    @Headers({"Accept: application/json"})
    @POST("change_language")
    Call<ChangeLangResponseModel> changeLang(@Header("Authorization") String token,
                                             @Body ChangeLangRequestModel changeLangRequestModel);

    @Headers({"Accept: application/json"})
    @POST("upload_content")
    Call<UploadStoryResponseModel> uploadStory(@Header("Authorization") String token,
                                               @Body UploadStoryRequestModel uploadStoryRequestModel);

    @Headers({"Accept: application/json"})
    @POST("get/posts_by_user")
    Call<PostResponseModel> getPosts(@Header("Authorization") String token,
                                        @Body PostRequestModel postRequestModel);



}
