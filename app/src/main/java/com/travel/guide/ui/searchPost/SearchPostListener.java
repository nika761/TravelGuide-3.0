package com.travel.guide.ui.searchPost;

import com.travel.guide.model.response.PostResponse;

public interface SearchPostListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostError(String message);

}
