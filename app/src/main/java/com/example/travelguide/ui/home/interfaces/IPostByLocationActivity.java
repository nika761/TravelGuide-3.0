package com.example.travelguide.ui.home.interfaces;

import com.example.travelguide.model.response.PostResponse;

public interface IPostByLocationActivity {
    void onGetPosts(PostResponse postResponse);
    void onGetPostError(String message);
}
