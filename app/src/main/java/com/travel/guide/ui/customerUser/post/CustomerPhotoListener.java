package com.travel.guide.ui.customerUser.post;

import com.travel.guide.model.response.PostResponse;

public interface CustomerPhotoListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostsError(String message);

    void onPostChoose(int postId);

}
