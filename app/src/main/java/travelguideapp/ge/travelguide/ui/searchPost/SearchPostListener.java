package travelguideapp.ge.travelguide.ui.searchPost;

import java.util.List;

import travelguideapp.ge.travelguide.model.response.PostResponse;

public interface SearchPostListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onChoosePost(int postId);

    void onGetPostError(String message);

}
