package travelguideapp.ge.travelguide.ui.home.profile.favorites;

import travelguideapp.ge.travelguide.model.response.PostResponse;

import java.util.List;

public interface FavoritePostListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onGetPostsError(String message);

    void onLazyLoad(int postId);

    void onPostChoose(int postId);

}
