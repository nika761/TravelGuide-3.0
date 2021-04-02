package travelguideapp.ge.travelguide.ui.profile.posts;

import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.base.BaseListener;

import java.util.List;

public interface PostListener extends BaseListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onLazyLoad(int postId);

    void onPostChoose(int position);

}
