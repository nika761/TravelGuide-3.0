package travelguideapp.ge.travelguide.ui.searchPost;

import travelguideapp.ge.travelguide.model.response.PostResponse;

public interface SearchPostListener {

    void onGetPosts(PostResponse postResponse);

    void onGetPostError(String message);

}
