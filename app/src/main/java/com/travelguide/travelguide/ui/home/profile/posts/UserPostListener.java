package com.travelguide.travelguide.ui.home.profile.posts;

import com.travelguide.travelguide.model.response.PostResponse;

public interface UserPostListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);

}
