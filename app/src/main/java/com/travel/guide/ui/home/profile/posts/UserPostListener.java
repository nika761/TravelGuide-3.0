package com.travel.guide.ui.home.profile.posts;

import com.travel.guide.model.response.PostResponse;

public interface UserPostListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);

}
