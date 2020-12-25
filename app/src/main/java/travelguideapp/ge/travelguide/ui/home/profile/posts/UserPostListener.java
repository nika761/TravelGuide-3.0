package travelguideapp.ge.travelguide.ui.home.profile.posts;

import travelguideapp.ge.travelguide.model.response.PostResponse;

import java.util.List;

public interface UserPostListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onGetPostsError(String message);

    void onLazyLoad(int postId);

    void onPostChoose(int postId);

}
