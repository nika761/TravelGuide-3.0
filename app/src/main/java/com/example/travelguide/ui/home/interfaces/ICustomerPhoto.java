package com.example.travelguide.ui.home.interfaces;

import com.example.travelguide.model.response.PostResponse;

public interface ICustomerPhoto {
    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);
}
