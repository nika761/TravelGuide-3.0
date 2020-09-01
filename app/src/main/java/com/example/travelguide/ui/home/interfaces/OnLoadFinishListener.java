package com.example.travelguide.ui.home.interfaces;

import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;

public interface OnLoadFinishListener {

    void stopLoader();

    void startTimer(int postId);

    void resetTimer();

    void onStoryLikeChoose(int postId, int storyId);

    void onStoryLiked(SetStoryLikeResponse setStoryLikeResponse);

    void onStoryLikeError(String message);

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onFollowChoose(int userId);

    void onFollowSuccess(FollowResponse followResponse);

    void onFollowError(String message);

    void onShareChoose(String postLink);

    void onFavoriteChoose(int post_id);

    void onFavoriteSuccess(SetPostFavoriteResponse setPostFavoriteResponse);

    void onFavoriteError(String message);
}
