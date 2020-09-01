package com.example.travelguide.network;

import com.example.travelguide.model.request.AboutRequest;
import com.example.travelguide.model.request.AddFavoriteMusic;
import com.example.travelguide.model.request.ByMoodRequest;
import com.example.travelguide.model.request.ChangeLangRequest;
import com.example.travelguide.model.request.CustomerPostRequest;
import com.example.travelguide.model.request.FollowersRequest;
import com.example.travelguide.model.request.FollowingRequest;
import com.example.travelguide.model.request.ForgotPasswordRequest;
import com.example.travelguide.model.request.LoginRequest;
import com.example.travelguide.model.request.PostByLocationRequest;
import com.example.travelguide.model.request.PostRequest;
import com.example.travelguide.model.request.ProfileRequest;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.ResetPasswordRequest;
import com.example.travelguide.model.request.SearchMusicRequest;
import com.example.travelguide.model.request.SetPostFavoriteRequest;
import com.example.travelguide.model.request.SetPostViewRequest;
import com.example.travelguide.model.request.SetStoryLikeRequest;
import com.example.travelguide.model.request.SignUpRequest;
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.model.request.UploadPostRequest;
import com.example.travelguide.model.request.VerifyEmailRequest;
import com.example.travelguide.model.response.AboutResponse;
import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.ChangeLangResponse;
import com.example.travelguide.model.response.FavoriteMusicResponse;
import com.example.travelguide.model.response.FollowerResponse;
import com.example.travelguide.model.response.FollowingResponse;
import com.example.travelguide.model.response.ForgotPasswordResponse;
import com.example.travelguide.model.response.MoodResponse;
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.ProfileResponse;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.ResetPasswordResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetPostViewResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
import com.example.travelguide.model.response.SignUpResponse;
import com.example.travelguide.model.request.CheckMailRequest;
import com.example.travelguide.model.response.CheckMailResponse;
import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.LanguagesResponse;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.model.response.TermsPolicyResponse;
import com.example.travelguide.model.response.UploadPostResponse;
import com.example.travelguide.model.response.VerifyEmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST("check_email")
    Call<CheckMailResponse> checkEmail(@Body CheckMailRequest checkMailRequest);

    @POST("check_nick")
    Call<CheckNickResponse> checkNick(@Body CheckNickRequest checkNickRequest);

    @GET("get_languages")
    Call<LanguagesResponse> getLanguages();

    @POST("get_terms_policy")
    Call<TermsPolicyResponse> getTerms(@Body TermsPolicyRequest termsPolicyRequest);

    @POST("login")
    Call<LoginResponse> signIn(@Body LoginRequest loginRequest);

    @POST("get_about")
    Call<AboutResponse> getAbout(@Body AboutRequest aboutRequest);

    @Headers({"Accept: application/json"})
    @POST("change_language")
    Call<ChangeLangResponse> changeLanguage(@Header("Authorization") String token,
                                            @Body ChangeLangRequest changeLangRequest);

    @Headers({"Accept: application/json"})
    @POST("upload_content")
    Call<UploadPostResponse> uploadPost(@Header("Authorization") String token,
                                        @Body UploadPostRequest uploadPostRequest);

    @Headers({"Accept: application/json"})
    @POST("get/get_posts")
    Call<PostResponse> getPosts(@Header("Authorization") String token,
                                @Body PostRequest postRequest);

    @Headers({"Accept: application/json"})
    @POST("get/posts_by_user")
    Call<PostResponse> getCustomerPosts(@Header("Authorization") String token,
                                        @Body CustomerPostRequest customerPostRequest);

    @Headers({"Accept: application/json"})
    @POST("get/profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String token,
                                     @Body ProfileRequest profileRequest);

    @Headers({"Accept: application/json"})
    @POST("get/musics")
    Call<MusicResponse> getMusics(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("set/favorite_music")
    Call<AddFavoriteMusicResponse> addFavoriteMusic(@Header("Authorization") String token,
                                                    @Body AddFavoriteMusic addFavoriteMusic);

    @Headers({"Accept: application/json"})
    @POST("get/favorite_music")
    Call<FavoriteMusicResponse> getFavoriteMusics(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("set/follower")
    Call<FollowResponse> follow(@Header("Authorization") String token,
                                @Body FollowRequest followRequest);

    @Headers({"Accept: application/json"})
    @POST("get/followings")
    Call<FollowingResponse> getFollowing(@Header("Authorization") String token,
                                         @Body FollowingRequest followingRequest);


    @Headers({"Accept: application/json"})
    @POST("get/followers")
    Call<FollowerResponse> getFollowers(@Header("Authorization") String token,
                                        @Body FollowersRequest followersRequest);

    @Headers({"Accept: application/json"})
    @POST("get/moods")
    Call<MoodResponse> getMoods(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("get/mood/musics")
    Call<MusicResponse> getMusicsByMood(@Header("Authorization") String token,
                                        @Body ByMoodRequest byMoodRequest);

    @Headers({"Accept: application/json"})
    @POST("search/musics")
    Call<MusicResponse> searchMusic(@Header("Authorization") String token,
                                    @Body SearchMusicRequest searchMusicRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post/view")
    Call<SetPostViewResponse> setPostView(@Header("Authorization") String token,
                                          @Body SetPostViewRequest setPostViewRequest);

    @Headers({"Accept: application/json"})
    @POST("get/posts_by_location")
    Call<PostResponse> getPostsByLocation(@Header("Authorization") String token,
                                          @Body PostByLocationRequest postByLocationRequest);

    @Headers({"Accept: application/json"})
    @POST("email/verify_email")
    Call<VerifyEmailResponse> verifyEmail(@Header("Authorization") String token,
                                          @Body VerifyEmailRequest verifyEmailRequest);

    @Headers({"Accept: application/json"})
    @POST("set/story_like")
    Call<SetStoryLikeResponse> setStoryLike(@Header("Authorization") String token,
                                            @Body SetStoryLikeRequest setStoryLikeRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_favourite")
    Call<SetPostFavoriteResponse> setPostFavorite(@Header("Authorization") String token,
                                                  @Body SetPostFavoriteRequest setPostFavoriteRequest);

    @Headers({"Accept: application/json"})
    @POST("password/email")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @Headers({"Accept: application/json"})
    @POST("password/reset")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

}
