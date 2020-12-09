package com.travel.guide.ui.home.profile.posts;

import com.travel.guide.model.response.PostResponse;

import java.util.List;

public interface UserPostListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onGetPostsError(String message);

    void onLazyLoad(int postId);

    void onPostChoose(int postId);

}
