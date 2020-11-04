package com.travelguide.travelguide.ui.home.profile.favorites;

import com.travelguide.travelguide.model.response.PostResponse;

public interface FavoritePostListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);

}
