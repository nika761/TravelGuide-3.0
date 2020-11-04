package com.travelguide.travelguide.ui.searchPost;

import com.travelguide.travelguide.model.response.PostResponse;

public interface SearchPostListener {
    void onGetPosts(PostResponse postResponse);
    void onGetPostError(String message);
}
