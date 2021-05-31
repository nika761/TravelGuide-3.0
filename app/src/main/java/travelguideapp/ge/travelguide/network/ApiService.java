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

    @GET("get_languages")
    Call<LanguagesResponse> getLanguages();

    @POST("get/texts")
    Call<LanguageStringsResponse> getLanguageStrings(@Body LanguageStringsRequest languageStringsRequest);

    @GET("get/app_settings")
    Call<AppSettingsResponse> getAppSettings();

    @POST("get_terms_policy")
    Call<TermsPolicyResponse> getTerms(@Body TermsPolicyRequest termsPolicyRequest);

    @POST("get/about")
    Call<AboutResponse> getAbout(@Body AboutRequest aboutRequest);

    @POST("change_language")
    Call<ChangeLangResponse> changeLanguage(@Body ChangeLangRequest changeLangRequest);

    @POST("set/post_story_view")
    Call<SetPostViewResponse> setPostView(@Body SetPostViewRequest setPostViewRequest);

    @POST("get/get_posts")
    Call<PostResponse> getPosts(@Body PostRequest postRequest);

    @POST("get/posts_by_user")
    Call<PostResponse> getPostsByUser(@Body PostByUserRequest customerPostRequest);

    @POST("get/profile")
    Call<ProfileResponse> getProfile(@Body ProfileRequest profileRequest);

    @POST("set/profile")
    Call<UpdateProfileResponse> updateProfile(@Body UpdateProfileRequest updateProfileRequest);

    @POST("get/musics")
    Call<MusicResponse> getMusics();

    @POST("set/post_go_click")
    Call<Object> chooseGo(@Body ChooseGoRequest chooseGoRequest);

    @POST("set/favorite_music")
    Call<AddFavoriteMusicResponse> addFavoriteMusic(@Body AddFavoriteMusic addFavoriteMusic);

    @POST("get/favorite_music")
    Call<FavoriteMusicResponse> getFavoriteMusics();

    @POST("set/follower")
    Call<FollowResponse> follow(@Body FollowRequest followRequest);

    @POST("get/followings")
    Call<FollowingResponse> getFollowing(@Body FollowingRequest followingRequest);

    @POST("get/followers")
    Call<FollowerResponse> getFollowers(@Body FollowersRequest followersRequest);

    @POST("get/moods")
    Call<MoodResponse> getMoods();

    @POST("get/mood/musics")
    Call<MusicResponse> getMusicsByMood(@Body ByMoodRequest byMoodRequest);


    @POST("search/musics")
    Call<MusicResponse> searchMusic(@Body SearchMusicRequest searchMusicRequest);


    @POST("search/all")
    Call<FullSearchResponse> searchAll(@Body FullSearchRequest fullSearchRequest);


    @POST("get/posts_by_location")
    Call<PostResponse> getPostsByLocation(@Body PostByLocationRequest postByLocationRequest);


    @POST("get/posts_by_hashtag")
    Call<PostResponse> getPostsByHashtag(@Body PostByHashtagRequest postByHashtagRequest);

    @POST("set/story_like")
    Call<SetStoryLikeResponse> setStoryLike(@Body SetStoryLikeRequest setStoryLikeRequest);


    @POST("set/post_favourite")
    Call<SetPostFavoriteResponse> setPostFavorite(@Body SetPostFavoriteRequest setPostFavoriteRequest);

    @POST("delete/post")
    Call<DeleteStoryResponse> deleteStory(@Body DeleteStoryRequest deleteStoryRequest);

    @POST("get/my_favourite_posts")
    Call<PostResponse> getFavoritePosts(@Body FavoritePostRequest favoritePostRequest);

    @POST("set/post_share")
    Call<SharePostResponse> setPostShare(@Body SharePostRequest sharePostRequest);

    @POST("search/hashtags")
    Call<HashtagResponse> getHashtags(@Body SearchHashtagRequest hashtagRequest);

    @POST("search/followers")
    Call<FollowerResponse> searchFollowers(@Body SearchFollowersRequest searchFollowersRequest);

    @POST("get/post_story_comments")
    Call<CommentResponse> getStoryComments(@Body CommentRequest commentRequest);

    @POST("get/post_comment_replies")
    Call<MoreReplyResponse> getMoreCommentReplies(@Body GetMoreCommentRequest moreCommentRequest);

    @POST("set/post_story_comment")
    Call<AddCommentResponse> addStoryComment(@Body AddCommentRequest addCommentRequest);

    @POST("delete/post_story_comment")
    Call<DeleteCommentResponse> deleteStoryComment(@Body DeleteCommentRequest deleteCommentRequest);

    @POST("delete/post_story_comment_reply")
    Call<DeleteReplyResponse> deleteStoryCommentReply(@Body DeleteReplyRequest deleteReplyRequest);

    @POST("set/post_story_comment_reply")
    Call<AddCommentReplyResponse> addStoryCommentReply(@Body AddCommentReplyRequest replyRequest);

    @POST("set/post_story_comment_like")
    Call<LikeCommentResponse> likeStoryComment(@Body LikeCommentRequest likeCommentRequest);

    @POST("set/post_story_comment_reply_like")
    Call<LikeCommentReplyResponse> likeCommentReply(@Body LikeCommentReplyRequest likeCommentReplyRequest);

    @POST("create/post")
    Call<UploadPostResponse> uploadPost(
            @Header("Content-type") String unc,
            @Body UploadPostRequest uploadPostRequest);
}
