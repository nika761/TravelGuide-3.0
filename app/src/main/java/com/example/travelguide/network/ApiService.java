package com.example.travelguide.network;

import com.example.travelguide.model.request.AboutRequest;
import com.example.travelguide.model.request.AddCommentReplyRequest;
import com.example.travelguide.model.request.AddCommentRequest;
import com.example.travelguide.model.request.AddFavoriteMusic;
import com.example.travelguide.model.request.AuthWitFirebaseRequest;
import com.example.travelguide.model.request.ByMoodRequest;
import com.example.travelguide.model.request.ChangeLangRequest;
import com.example.travelguide.model.request.CommentRequest;
import com.example.travelguide.model.request.LikeCommentRequest;
import com.example.travelguide.model.request.GetMoreCommentRequest;
import com.example.travelguide.model.request.PostByUserRequest;
import com.example.travelguide.model.request.FavoritePostRequest;
import com.example.travelguide.model.request.FollowersRequest;
import com.example.travelguide.model.request.FollowingRequest;
import com.example.travelguide.model.request.ForgotPasswordRequest;
import com.example.travelguide.model.request.SearchFollowersRequest;
import com.example.travelguide.model.request.SearchHashtagRequest;
import com.example.travelguide.model.request.LoginRequest;
import com.example.travelguide.model.request.PostByHashtagRequest;
import com.example.travelguide.model.request.PostByLocationRequest;
import com.example.travelguide.model.request.PostRequest;
import com.example.travelguide.model.request.ProfileRequest;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.ResetPasswordRequest;
import com.example.travelguide.model.request.SearchMusicRequest;
import com.example.travelguide.model.request.SetPostFavoriteRequest;
import com.example.travelguide.model.request.SetPostViewRequest;
import com.example.travelguide.model.request.SetStoryLikeRequest;
import com.example.travelguide.model.request.SharePostRequest;
import com.example.travelguide.model.request.SignUpRequest;
import com.example.travelguide.model.request.SignUpWithFirebaseRequest;
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.model.request.UploadPostRequestModel;
import com.example.travelguide.model.request.VerifyEmailRequest;
import com.example.travelguide.model.response.AboutResponse;
import com.example.travelguide.model.response.AddCommentReplyResponse;
import com.example.travelguide.model.response.AddCommentResponse;
import com.example.travelguide.model.response.AddFavoriteMusicResponse;
import com.example.travelguide.model.response.AppSettingsResponse;
import com.example.travelguide.model.response.AuthWithFirebaseResponse;
import com.example.travelguide.model.response.ChangeLangResponse;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.model.response.FavoriteMusicResponse;
import com.example.travelguide.model.response.FollowerResponse;
import com.example.travelguide.model.response.FollowingResponse;
import com.example.travelguide.model.response.ForgotPasswordResponse;
import com.example.travelguide.model.response.HashtagResponse;
import com.example.travelguide.model.response.LikeCommentResponse;
import com.example.travelguide.model.response.MoodResponse;
import com.example.travelguide.model.response.MoreReplyResponse;
import com.example.travelguide.model.response.MusicResponse;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.ProfileResponse;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.ResetPasswordResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetPostViewResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
import com.example.travelguide.model.response.SharePostResponse;
import com.example.travelguide.model.response.SignUpResponse;
import com.example.travelguide.model.request.CheckMailRequest;
import com.example.travelguide.model.response.CheckMailResponse;
import com.example.travelguide.model.request.CheckNickRequest;
import com.example.travelguide.model.response.CheckNickResponse;
import com.example.travelguide.model.response.LanguagesResponse;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.model.response.SignUpWithFirebaseResponse;
import com.example.travelguide.model.response.TermsPolicyResponse;
import com.example.travelguide.model.response.UploadPostResponse;
import com.example.travelguide.model.response.VerifyEmailResponse;

import java.util.List;

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

    @GET("get/app_settings")
    Call<AppSettingsResponse> getAppSettings();

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

//    @Headers({"Accept: application/json"})
//    @POST("upload_content")
//    Call<UploadPostResponse> uploadPost(@Header("Authorization") String token,
//                                        @Body UploadPostRequest uploadPostRequest);

    @Headers({"Accept: application/json"})
    @POST("get/get_posts")
    Call<PostResponse> getPosts(@Header("Authorization") String token,
                                @Body PostRequest postRequest);

    @Headers({"Accept: application/json"})
    @POST("get/posts_by_user")
    Call<PostResponse> getCustomerPosts(@Header("Authorization") String token,
                                        @Body PostByUserRequest customerPostRequest);

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
    @POST("get/posts_by_hashtag")
    Call<PostResponse> getPostsByHashtag(@Header("Authorization") String token,
                                         @Body PostByHashtagRequest postByHashtagRequest);

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

    @Headers({"Accept: application/json"})
    @POST("get/my_favourite_posts")
    Call<PostResponse> getFavoritePosts(@Header("Authorization") String token,
                                        @Body FavoritePostRequest favoritePostRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_share")
    Call<SharePostResponse> setPostShare(@Header("Authorization") String token,
                                         @Body SharePostRequest sharePostRequest);

    @Headers({"Accept: application/json"})
    @POST("search/hashtags")
    Call<HashtagResponse> getHashtags(@Header("Authorization") String token,
                                      @Body SearchHashtagRequest hashtagRequest);

    @Headers({"Accept: application/json"})
    @POST("search/followers")
    Call<FollowerResponse> searchFollowers(@Header("Authorization") String token,
                                           @Body SearchFollowersRequest searchFollowersRequest);

    @Headers({"Accept: application/json"})
    @POST("get/post_story_comments")
    Call<CommentResponse> getStoryComments(@Header("Authorization") String token,
                                           @Body CommentRequest commentRequest);

    @Headers({"Accept: application/json"})
    @POST("get/post_comment_replies")
    Call<MoreReplyResponse> getMoreCommentReplies(@Header("Authorization") String token,
                                                  @Body GetMoreCommentRequest moreCommentRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_story_comment")
    Call<AddCommentResponse> addStoryComment(@Header("Authorization") String token,
                                             @Body AddCommentRequest addCommentRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_story_comment_reply")
    Call<AddCommentReplyResponse> addStoryCommentReply(@Header("Authorization") String token,
                                                       @Body AddCommentReplyRequest replyRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_story_comment_like")
    Call<LikeCommentResponse> likeStoryComment(@Header("Authorization") String token,
                                               @Body LikeCommentRequest likeCommentRequest);


    @Headers({"Accept: application/json"})
    @POST("login/by_outer_account")
    Call<AuthWithFirebaseResponse> authWithFirebase(@Body AuthWitFirebaseRequest authWitFirebaseRequest);

    @Headers({"Accept: application/json"})
    @POST("register_outer_account")
    Call<SignUpWithFirebaseResponse> signUpWithFirebase(@Body SignUpWithFirebaseRequest signUpWithFirebaseRequest);

    @Headers({"Accept: application/json"})
    @POST("create/post")
    Call<UploadPostResponse> uploadPost(@Header("Authorization") String token,
                                        @Header("Content-type") String unc,
                                        @Body UploadPostRequestModel uploadPostRequest);
}
