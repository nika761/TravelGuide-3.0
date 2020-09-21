package com.example.travelguide.ui.home.profile.favorites;

import com.example.travelguide.model.response.PostResponse;

public interface FavoritePostListener {
    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);
}
