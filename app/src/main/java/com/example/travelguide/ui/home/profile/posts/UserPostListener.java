package com.example.travelguide.ui.home.profile.posts;

import com.example.travelguide.model.response.PostResponse;

public interface UserPostListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose();

}
