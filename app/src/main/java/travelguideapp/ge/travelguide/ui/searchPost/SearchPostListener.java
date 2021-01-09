package travelguideapp.ge.travelguide.ui.searchPost;

import travelguideapp.ge.travelguide.model.response.PostResponse;

public interface SearchPostListener {

    void onGetPosts(PostResponse postResponse);

    void onChoosePost(int postId);

    void onGetPostError(String message);

}
