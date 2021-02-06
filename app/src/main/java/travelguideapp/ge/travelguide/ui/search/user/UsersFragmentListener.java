package travelguideapp.ge.travelguide.ui.search.user;

import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;

public interface UsersFragmentListener {
    void onUserChoose(FullSearchResponse.Users user);
}
