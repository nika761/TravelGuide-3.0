package travelguideapp.ge.travelguide.ui.profile.posts;

import java.util.List;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.PostResponse;

public interface PostListener extends BaseViewListener {

    void onGetPosts(List<PostResponse.Posts> posts);

    void onLazyLoad(int postId);

    void onPostChoose(int position);

}
