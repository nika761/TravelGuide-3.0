package com.travelguide.travelguide.ui.customerUser.post;

import com.travelguide.travelguide.model.response.PostResponse;

public interface CustomerPhotoListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);

}
