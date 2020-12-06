package com.travel.guide.network;

import com.travel.guide.model.request.ChangePasswordRequest;
import com.travel.guide.model.request.AboutRequest;
import com.travel.guide.model.request.AddCommentReplyRequest;
import com.travel.guide.model.request.AddCommentRequest;
import com.travel.guide.model.request.AddFavoriteMusic;
import com.travel.guide.model.request.AuthWitFirebaseRequest;
import com.travel.guide.model.request.ByMoodRequest;
import com.travel.guide.model.request.ChangeLangRequest;
import com.travel.guide.model.request.CommentRequest;
import com.travel.guide.model.request.DeleteCommentRequest;
import com.travel.guide.model.request.DeleteReplyRequest;
import com.travel.guide.model.request.LanguageStringsRequest;
import com.travel.guide.model.request.LikeCommentReplyRequest;
import com.travel.guide.model.request.LikeCommentRequest;
import com.travel.guide.model.request.GetMoreCommentRequest;
import com.travel.guide.model.request.PostByUserRequest;
import com.travel.guide.model.request.FavoritePostRequest;
import com.travel.guide.model.request.FollowersRequest;
import com.travel.guide.model.request.FollowingRequest;
import com.travel.guide.model.request.ForgotPasswordRequest;
import com.travel.guide.model.request.SearchFollowersRequest;
import com.travel.guide.model.request.SearchHashtagRequest;
import com.travel.guide.model.request.LoginRequest;
import com.travel.guide.model.request.PostByHashtagRequest;
import com.travel.guide.model.request.PostByLocationRequest;
import com.travel.guide.model.request.PostRequest;
import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.request.FollowRequest;
import com.travel.guide.model.request.ResetPasswordRequest;
import com.travel.guide.model.request.SearchMusicRequest;
import com.travel.guide.model.request.SetPostFavoriteRequest;
import com.travel.guide.model.request.SetPostViewRequest;
import com.travel.guide.model.request.SetStoryLikeRequest;
import com.travel.guide.model.request.SharePostRequest;
import com.travel.guide.model.request.SignUpRequest;
import com.travel.guide.model.request.SignUpWithFirebaseRequest;
import com.travel.guide.model.request.TermsPolicyRequest;
import com.travel.guide.model.request.UpdateProfileRequest;
import com.travel.guide.model.request.UploadPostRequest;
import com.travel.guide.model.request.VerifyEmailRequest;
import com.travel.guide.model.response.AboutResponse;
import com.travel.guide.model.response.AddCommentReplyResponse;
import com.travel.guide.model.response.AddCommentResponse;
import com.travel.guide.model.response.AddFavoriteMusicResponse;
import com.travel.guide.model.response.AppSettingsResponse;
import com.travel.guide.model.response.AuthWithFirebaseResponse;
import com.travel.guide.model.response.ChangeLangResponse;
import com.travel.guide.model.response.ChangePasswordResponse;
import com.travel.guide.model.response.CommentResponse;
import com.travel.guide.model.response.DeleteCommentResponse;
import com.travel.guide.model.response.DeleteReplyResponse;
import com.travel.guide.model.response.FavoriteMusicResponse;
import com.travel.guide.model.response.FollowerResponse;
import com.travel.guide.model.response.FollowingResponse;
import com.travel.guide.model.response.ForgotPasswordResponse;
import com.travel.guide.model.response.HashtagResponse;
import com.travel.guide.model.response.LanguageStringsResponse;
import com.travel.guide.model.response.LikeCommentReplyResponse;
import com.travel.guide.model.response.LikeCommentResponse;
import com.travel.guide.model.response.MoodResponse;
import com.travel.guide.model.response.MoreReplyResponse;
import com.travel.guide.model.response.MusicResponse;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.ResetPasswordResponse;
import com.travel.guide.model.response.SetPostFavoriteResponse;
import com.travel.guide.model.response.SetPostViewResponse;
import com.travel.guide.model.response.SetStoryLikeResponse;
import com.travel.guide.model.response.SharePostResponse;
import com.travel.guide.model.response.SignUpResponse;
import com.travel.guide.model.request.CheckMailRequest;
import com.travel.guide.model.response.CheckMailResponse;
import com.travel.guide.model.request.CheckNickRequest;
import com.travel.guide.model.response.CheckNickResponse;
import com.travel.guide.model.response.LanguagesResponse;
import com.travel.guide.model.response.LoginResponse;
import com.travel.guide.model.response.SignUpWithFirebaseResponse;
import com.travel.guide.model.response.TermsPolicyResponse;
import com.travel.guide.model.response.UpdateProfileResponse;
import com.travel.guide.model.response.UploadPostResponse;
import com.travel.guide.model.response.VerifyEmailResponse;

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

    @POST("get/texts")
    Call<LanguageStringsResponse> getLanguageStrings(@Body LanguageStringsRequest languageStringsRequest);

    @GET("get/app_settings")
    Call<AppSettingsResponse> getAppSettings();

    @POST("get_terms_policy")
    Call<TermsPolicyResponse> getTerms(@Body TermsPolicyRequest termsPolicyRequest);

    @POST("login")
    Call<LoginResponse> signIn(@Body LoginRequest loginRequest);

    @POST("get/about")
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
    Call<PostResponse> getPostsByUser(@Header("Authorization") String token,
                                      @Body PostByUserRequest customerPostRequest);

    @Headers({"Accept: application/json"})
    @POST("get/profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String token,
                                     @Body ProfileRequest profileRequest);

    @Headers({"Accept: application/json"})
    @POST("set/profile")
    Call<UpdateProfileResponse> updateProfile(@Header("Authorization") String token,
                                              @Body UpdateProfileRequest updateProfileRequest);

    @Headers({"Accept: application/json"})
    @POST("get/musics")
    Call<MusicResponse> getMusics(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("set/favorite_music")
    Call<AddFavoriteMusicResponse> addFavoriteMusic(@Header("Authorization") String token,
                                                    @Body AddFavoriteMusic addFavoriteMusic);

    @Headers({"Accept: application/json"})
    @POST("change/password")
    Call<ChangePasswordResponse> changePassword(@Header("Authorization") String token, @Body ChangePasswordRequest changePasswordRequest);

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
    @POST("delete/post_story_comment")
    Call<DeleteCommentResponse> deleteStoryComment(@Header("Authorization") String token,
                                                   @Body DeleteCommentRequest deleteCommentRequest);

    @Headers({"Accept: application/json"})
    @POST("delete/post_story_comment_reply")
    Call<DeleteReplyResponse> deleteStoryCommentReply(@Header("Authorization") String token,
                                                      @Body DeleteReplyRequest deleteReplyRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_story_comment_reply")
    Call<AddCommentReplyResponse> addStoryCommentReply(@Header("Authorization") String token,
                                                       @Body AddCommentReplyRequest replyRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_story_comment_like")
    Call<LikeCommentResponse> likeStoryComment(@Header("Authorization") String token,
                                               @Body LikeCommentRequest likeCommentRequest);

    @Headers({"Accept: application/json"})
    @POST("set/post_story_comment_reply_like")
    Call<LikeCommentReplyResponse> likeCommentReply(@Header("Authorization") String token,
                                                    @Body LikeCommentReplyRequest likeCommentReplyRequest);


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
                                        @Body UploadPostRequest uploadPostRequest);
}
