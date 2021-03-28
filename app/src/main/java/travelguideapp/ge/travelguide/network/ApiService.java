package travelguideapp.ge.travelguide.network;

import travelguideapp.ge.travelguide.model.request.ChangePasswordRequest;
import travelguideapp.ge.travelguide.model.request.AboutRequest;
import travelguideapp.ge.travelguide.model.request.AddCommentReplyRequest;
import travelguideapp.ge.travelguide.model.request.AddCommentRequest;
import travelguideapp.ge.travelguide.model.request.AddFavoriteMusic;
import travelguideapp.ge.travelguide.model.request.AuthWitFirebaseRequest;
import travelguideapp.ge.travelguide.model.request.ByMoodRequest;
import travelguideapp.ge.travelguide.model.request.ChangeLangRequest;
import travelguideapp.ge.travelguide.model.request.ChooseGoRequest;
import travelguideapp.ge.travelguide.model.request.CommentRequest;
import travelguideapp.ge.travelguide.model.request.DeleteCommentRequest;
import travelguideapp.ge.travelguide.model.request.DeleteReplyRequest;
import travelguideapp.ge.travelguide.model.request.DeleteStoryRequest;
import travelguideapp.ge.travelguide.model.request.FullSearchRequest;
import travelguideapp.ge.travelguide.model.request.LanguageStringsRequest;
import travelguideapp.ge.travelguide.model.request.LikeCommentReplyRequest;
import travelguideapp.ge.travelguide.model.request.LikeCommentRequest;
import travelguideapp.ge.travelguide.model.request.GetMoreCommentRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.FollowersRequest;
import travelguideapp.ge.travelguide.model.request.FollowingRequest;
import travelguideapp.ge.travelguide.model.request.ForgotPasswordRequest;
import travelguideapp.ge.travelguide.model.request.SearchFollowersRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.request.LoginRequest;
import travelguideapp.ge.travelguide.model.request.PostByHashtagRequest;
import travelguideapp.ge.travelguide.model.request.PostByLocationRequest;
import travelguideapp.ge.travelguide.model.request.PostRequest;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.ResetPasswordRequest;
import travelguideapp.ge.travelguide.model.request.SearchMusicRequest;
import travelguideapp.ge.travelguide.model.request.SetCommentReportRequest;
import travelguideapp.ge.travelguide.model.request.SetPostFavoriteRequest;
import travelguideapp.ge.travelguide.model.request.SetPostViewRequest;
import travelguideapp.ge.travelguide.model.request.SetPostReportRequest;
import travelguideapp.ge.travelguide.model.request.SetStoryLikeRequest;
import travelguideapp.ge.travelguide.model.request.SetUserReportRequest;
import travelguideapp.ge.travelguide.model.request.SharePostRequest;
import travelguideapp.ge.travelguide.model.request.SignUpRequest;
import travelguideapp.ge.travelguide.model.request.SignUpWithFirebaseRequest;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.model.request.UpdateProfileRequest;
import travelguideapp.ge.travelguide.model.request.UploadPostRequest;
import travelguideapp.ge.travelguide.model.request.VerifyEmailRequest;
import travelguideapp.ge.travelguide.model.response.AboutResponse;
import travelguideapp.ge.travelguide.model.response.AddCommentReplyResponse;
import travelguideapp.ge.travelguide.model.response.AddCommentResponse;
import travelguideapp.ge.travelguide.model.response.AddFavoriteMusicResponse;
import travelguideapp.ge.travelguide.model.response.AppSettingsResponse;
import travelguideapp.ge.travelguide.model.response.AuthWithFirebaseResponse;
import travelguideapp.ge.travelguide.model.response.ChangeLangResponse;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;
import travelguideapp.ge.travelguide.model.response.CommentResponse;
import travelguideapp.ge.travelguide.model.response.DeleteCommentResponse;
import travelguideapp.ge.travelguide.model.response.DeleteReplyResponse;
import travelguideapp.ge.travelguide.model.response.DeleteStoryResponse;
import travelguideapp.ge.travelguide.model.response.FavoriteMusicResponse;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;
import travelguideapp.ge.travelguide.model.response.ForgotPasswordResponse;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.model.response.LanguageStringsResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentReplyResponse;
import travelguideapp.ge.travelguide.model.response.LikeCommentResponse;
import travelguideapp.ge.travelguide.model.response.MoodResponse;
import travelguideapp.ge.travelguide.model.response.MoreReplyResponse;
import travelguideapp.ge.travelguide.model.response.MusicResponse;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.ResetPasswordResponse;
import travelguideapp.ge.travelguide.model.response.SetPostFavoriteResponse;
import travelguideapp.ge.travelguide.model.response.SetPostViewResponse;
import travelguideapp.ge.travelguide.model.response.SetReportResponse;
import travelguideapp.ge.travelguide.model.response.SetStoryLikeResponse;
import travelguideapp.ge.travelguide.model.response.SharePostResponse;
import travelguideapp.ge.travelguide.model.response.SignUpResponse;
import travelguideapp.ge.travelguide.model.request.CheckMailRequest;
import travelguideapp.ge.travelguide.model.response.CheckMailResponse;
import travelguideapp.ge.travelguide.model.request.CheckNickRequest;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;
import travelguideapp.ge.travelguide.model.response.LoginResponse;
import travelguideapp.ge.travelguide.model.response.SignUpWithFirebaseResponse;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.model.response.UpdateProfileResponse;
import travelguideapp.ge.travelguide.model.response.UploadPostResponse;
import travelguideapp.ge.travelguide.model.response.VerifyEmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers({"Accept: application/json"})
    @POST("register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @Headers({"Accept: application/json"})
    @POST("check_email")
    Call<CheckMailResponse> checkEmail(@Body CheckMailRequest checkMailRequest);

    @Headers({"Accept: application/json"})
    @POST("check_nick")
    Call<CheckNickResponse> checkNick(@Body CheckNickRequest checkNickRequest);

    @Headers({"Accept: application/json"})
    @GET("get_languages")
    Call<LanguagesResponse> getLanguages();

    @Headers({"Accept: application/json"})
    @POST("get/texts")
    Call<LanguageStringsResponse> getLanguageStrings(@Body LanguageStringsRequest languageStringsRequest);

    @Headers({"Accept: application/json"})
    @GET("get/app_settings")
    Call<AppSettingsResponse> getAppSettings();

    @Headers({"Accept: application/json"})
    @POST("get_terms_policy")
    Call<TermsPolicyResponse> getTerms(@Body TermsPolicyRequest termsPolicyRequest);

    @Headers({"Accept: application/json"})
    @POST("login")
    Call<LoginResponse> signIn(@Body LoginRequest loginRequest);

    @Headers({"Accept: application/json"})
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
    @POST("set/post_story_view")
    Call<SetPostViewResponse> setPostView(@Header("Authorization") String token,
                                          @Body SetPostViewRequest setPostViewRequest);

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
    @POST("set/post_go_click")
    Call<Object> chooseGo(@Header("Authorization") String token,
                          @Body ChooseGoRequest chooseGoRequest);

    @Headers({"Accept: application/json"})
    @POST("set/favorite_music")
    Call<AddFavoriteMusicResponse> addFavoriteMusic(@Header("Authorization") String token,
                                                    @Body AddFavoriteMusic addFavoriteMusic);

    @Headers({"Accept: application/json"})
    @POST("change/password")
    Call<ChangePasswordResponse> changePassword(@Header("Authorization") String token,
                                                @Body ChangePasswordRequest changePasswordRequest);

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
    @POST("search/all")
    Call<FullSearchResponse> searchAll(@Header("Authorization") String token,
                                       @Body FullSearchRequest fullSearchRequest);

//    @Headers({"Accept: application/json"})
//    @POST("set/post/view")
//    Call<SetPostViewResponse> setPostView(@Header("Authorization") String token,
//                                          @Body SetPostViewRequest setPostViewRequest);

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
    @POST("set/post_report")
    Call<SetReportResponse> setPostReport(@Header("Authorization") String token,
                                          @Body SetPostReportRequest setReportRequest);

    @Headers({"Accept: application/json"})
    @POST("set/user_report")
    Call<SetReportResponse> setUserReport(@Header("Authorization") String token,
                                          @Body SetUserReportRequest setUserReportRequest);

    @Headers({"Accept: application/json"})
    @POST("set/comment_report")
    Call<SetReportResponse> setCommentReport(@Header("Authorization") String token,
                                             @Body SetCommentReportRequest setCommentReportRequest);

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
    @POST("delete/post")
    Call<DeleteStoryResponse> deleteStory(@Header("Authorization") String token,
                                          @Body DeleteStoryRequest deleteStoryRequest);

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
