package travelguideapp.ge.travelguide.ui.profile.posts;

import java.util.List;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.PostResponse;

public interface PostListener extends BaseListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onLazyLoad(int postId);

    void onPostChoose(int position);

}
