package com.example.travelguide.ui.customerUser.post;

import com.example.travelguide.model.response.PostResponse;

public interface CustomerPhotoListener {
    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);
}
