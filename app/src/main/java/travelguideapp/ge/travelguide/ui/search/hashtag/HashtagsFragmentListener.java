package travelguideapp.ge.travelguide.ui.search.hashtag;

import travelguideapp.ge.travelguide.model.response.HashtagResponse;

public interface HashtagsFragmentListener {

    void onHashtagChoose(HashtagResponse.Hashtags hashtag);

    void onLazyLoad(int page);

}
