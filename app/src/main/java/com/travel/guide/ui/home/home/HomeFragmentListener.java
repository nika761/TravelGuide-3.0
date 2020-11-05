package com.travel.guide.ui.home.home;

import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.model.response.SetPostFavoriteResponse;
import com.travel.guide.model.response.SetStoryLikeResponse;
import com.travel.guide.model.response.SharePostResponse;

import java.util.List;

public interface HomeFragmentListener {

    void stopLoader();

    void onStoryLikeChoose(int postId, int storyId, int position);

    void onStoryLiked(SetStoryLikeResponse setStoryLikeResponse);


    void onGetPosts(List<PostResponse.Posts> posts);


    void onFollowChoose(int userId);

    void onFollowSuccess(FollowResponse followResponse);


    void onFavoriteChoose(int post_id, int position);

    void onFavoriteSuccess(SetPostFavoriteResponse setPostFavoriteResponse);


    void onShareChoose(String postLink, int post_id);

    void onShareSuccess(SharePostResponse sharePostResponse);


    void onCommentChoose(int storyId, int postId);

    void onUserChoose(int userId);


    void onLazyLoad(int fromPostId);

    void onError(String message);


    void onAuthError();


}
