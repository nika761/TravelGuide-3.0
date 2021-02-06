package travelguideapp.ge.travelguide.ui.search;

import java.util.List;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;

public interface SearchListener extends BaseListener {

    void onGetHashtags(List<HashtagResponse.Hashtags> hashtags);

    void onGetUsers(List<FollowerResponse.Followers> followers);

    void onGetSearchedData(FullSearchResponse fullSearchResponse);

}
