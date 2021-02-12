package travelguideapp.ge.travelguide.ui.search.posts;

import java.util.List;

import travelguideapp.ge.travelguide.model.response.PostResponse;

public interface PostByLocationListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onGetPostError(String message);

}
