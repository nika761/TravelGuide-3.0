package travelguideapp.ge.travelguide.ui.search;

import java.util.List;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.model.response.PostResponse;

public interface SearchListener extends BaseViewListener {

    void onGetHashtags(List<HashtagResponse.Hashtags> hashtags);

    void onGetUsers(List<FollowerResponse.Followers> followers);

    void onGetPosts(List<PostResponse.Posts> posts);

    void onGetSearchedData(FullSearchResponse fullSearchResponse);

}
