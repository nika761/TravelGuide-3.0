package com.travelguide.travelguide.network;

import com.travelguide.travelguide.model.request.AboutRequest;
import com.travelguide.travelguide.model.request.AddCommentReplyRequest;
import com.travelguide.travelguide.model.request.AddCommentRequest;
import com.travelguide.travelguide.model.request.AddFavoriteMusic;
import com.travelguide.travelguide.model.request.AuthWitFirebaseRequest;
import com.travelguide.travelguide.model.request.ByMoodRequest;
import com.travelguide.travelguide.model.request.ChangeLangRequest;
import com.travelguide.travelguide.model.request.CommentRequest;
import com.travelguide.travelguide.model.request.DeleteCommentRequest;
import com.travelguide.travelguide.model.request.DeleteReplyRequest;
import com.travelguide.travelguide.model.request.LikeCommentReplyRequest;
import com.travelguide.travelguide.model.request.LikeCommentRequest;
import com.travelguide.travelguide.model.request.GetMoreCommentRequest;
import com.travelguide.travelguide.model.request.PostByUserRequest;
import com.travelguide.travelguide.model.request.FavoritePostRequest;
import com.travelguide.travelguide.model.request.FollowersRequest;
import com.travelguide.travelguide.model.request.FollowingRequest;
import com.travelguide.travelguide.model.request.ForgotPasswordRequest;
import com.travelguide.travelguide.model.request.SearchFollowersRequest;
import com.travelguide.travelguide.model.request.SearchHashtagRequest;
import com.travelguide.travelguide.model.request.LoginRequest;
import com.travelguide.travelguide.model.request.PostByHashtagRequest;
import com.travelguide.travelguide.model.request.PostByLocationRequest;
import com.travelguide.travelguide.model.request.PostRequest;
import com.travelguide.travelguide.model.request.ProfileRequest;
import com.travelguide.travelguide.model.request.FollowRequest;
import com.travelguide.travelguide.model.request.ResetPasswordRequest;
import com.travelguide.travelguide.model.request.SearchMusicRequest;
import com.travelguide.travelguide.model.request.SetPostFavoriteRequest;
import com.travelguide.travelguide.model.request.SetPostViewRequest;
import com.travelguide.travelguide.model.request.SetStoryLikeRequest;
import com.travelguide.travelguide.model.request.SharePostRequest;
import com.travelguide.travelguide.model.request.SignUpRequest;
import com.travelguide.travelguide.model.request.SignUpWithFirebaseRequest;
import com.travelguide.travelguide.model.request.TermsPolicyRequest;
import com.travelguide.travelguide.model.request.UploadPostRequestModel;
import com.travelguide.travelguide.model.request.VerifyEmailRequest;
import com.travelguide.travelguide.model.response.AboutResponse;
import com.travelguide.travelguide.model.response.AddCommentReplyResponse;
import com.travelguide.travelguide.model.response.AddCommentResponse;
import com.travelguide.travelguide.model.response.AddFavoriteMusicResponse;
import com.travelguide.travelguide.model.response.AppSettingsResponse;
import com.travelguide.travelguide.model.response.AuthWithFirebaseResponse;
import com.travelguide.travelguide.model.response.ChangeLangResponse;
import com.travelguide.travelguide.model.response.CommentResponse;
import com.travelguide.travelguide.model.response.DeleteCommentResponse;
import com.travelguide.travelguide.model.response.DeleteReplyResponse;
import com.travelguide.travelguide.model.response.FavoriteMusicResponse;
import com.travelguide.travelguide.model.response.FollowerResponse;
import com.travelguide.travelguide.model.response.FollowingResponse;
import com.travelguide.travelguide.model.response.ForgotPasswordResponse;
import com.travelguide.travelguide.model.response.HashtagResponse;
import com.travelguide.travelguide.model.response.LikeCommentReplyResponse;
import com.travelguide.travelguide.model.response.LikeCommentResponse;
import com.travelguide.travelguide.model.response.MoodResponse;
import com.travelguide.travelguide.model.response.MoreReplyResponse;
import com.travelguide.travelguide.model.response.MusicResponse;
import com.travelguide.travelguide.model.response.PostResponse;
import com.travelguide.travelguide.model.response.ProfileResponse;
import com.travelguide.travelguide.model.response.FollowResponse;
import com.travelguide.travelguide.model.response.ResetPasswordResponse;
import com.travelguide.travelguide.model.response.SetPostFavoriteResponse;
import com.travelguide.travelguide.model.response.SetPostViewResponse;
import com.travelguide.travelguide.model.response.SetStoryLikeResponse;
import com.travelguide.travelguide.model.response.SharePostResponse;
import com.travelguide.travelguide.model.response.SignUpResponse;
import com.travelguide.travelguide.model.request.CheckMailRequest;
import com.travelguide.travelguide.model.response.CheckMailResponse;
import com.travelguide.travelguide.model.request.CheckNickRequest;
import com.travelguide.travelguide.model.response.CheckNickResponse;
import com.travelguide.travelguide.model.response.LanguagesResponse;
import com.travelguide.travelguide.model.response.LoginResponse;
import com.travelguide.travelguide.model.response.SignUpWithFirebaseResponse;
import com.travelguide.travelguide.model.response.TermsPolicyResponse;
import com.travelguide.travelguide.model.response.UploadPostResponse;
import com.travelguide.travelguide.model.response.VerifyEmailResponse;

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
                                        @Body UploadPostRequestModel uploadPostRequest);
}
