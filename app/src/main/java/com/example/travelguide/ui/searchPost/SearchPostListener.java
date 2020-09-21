package com.example.travelguide.ui.searchPost;

import com.example.travelguide.model.response.PostResponse;

public interface SearchPostListener {
    void onGetPosts(PostResponse postResponse);
    void onGetPostError(String message);
}
