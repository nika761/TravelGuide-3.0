package com.travel.guide.ui.home.profile.favorites;

import com.travel.guide.model.response.PostResponse;

public interface FavoritePostListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);

}
