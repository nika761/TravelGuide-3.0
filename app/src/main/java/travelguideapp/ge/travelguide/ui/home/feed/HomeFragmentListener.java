package travelguideapp.ge.travelguide.ui.home.feed;

import travelguideapp.ge.travelguide.model.parcelable.PostSearchParams;
import travelguideapp.ge.travelguide.model.response.DeleteStoryResponse;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.model.response.SharePostResponse;

import java.util.List;

public interface HomeFragmentListener {

    void stopLoader();


    void onStoryLikeChoose(int postId, int storyId);

    void onStoryLiked();

    void onStoryUnLiked();


    void onGetPosts(List<PostResponse.Posts> posts);

    void onLocationChoose(int postId, PostSearchParams.SearchBy searchBy);

    void onHashtagChoose(String hashtag, PostSearchParams.SearchBy searchBy);


    void onFollowChoose(int userId);

    void onFollowAdded();

    void onFollowRemoved();


    void onFavoriteChoose(int post_id);

    void onFavoriteAdded();

    void onFavoriteRemoved();


    void onGoChoose(String url, int post_id);


    void onShareChoose(String postLink, int post_id);

    void onShared(SharePostResponse sharePostResponse);


    void onCommentChoose(int storyId, int postId);

    void onUserChoose(int userId);


    void onReportChoose(int postId);

    void onChooseEditPost(PostResponse.Posts post, int position);

    void onStoryDeleted(DeleteStoryResponse deleteStoryResponse);


    void onLazyLoad(int fromPostId);

    void onError(String message);

}
