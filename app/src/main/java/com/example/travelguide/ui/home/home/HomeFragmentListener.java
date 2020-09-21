package com.example.travelguide.ui.home.home;

import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
import com.example.travelguide.model.response.SharePostResponse;

import java.util.List;

public interface HomeFragmentListener {

    void stopLoader();

    void startTimer(int postId);

    void resetTimer();

    void onStoryLikeChoose(int postId, int storyId, int position);

    void onStoryLiked(SetStoryLikeResponse setStoryLikeResponse);

    void onStoryLikeError(String message);

    void onGetPosts(PostResponse postResponse);

    void onGetLazyPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onFollowChoose(int userId);

    void onFollowSuccess(FollowResponse followResponse);

    void onFollowError(String message);

    void onFavoriteChoose(int post_id, int position);

    void onFavoriteSuccess(SetPostFavoriteResponse setPostFavoriteResponse);

    void onFavoriteError(String message);

    void onShareChoose(String postLink, int post_id);

    void onShareSuccess(SharePostResponse sharePostResponse);

    void onShareError(String message);

    void onCommentChoose(int storyId, int postId);
}
