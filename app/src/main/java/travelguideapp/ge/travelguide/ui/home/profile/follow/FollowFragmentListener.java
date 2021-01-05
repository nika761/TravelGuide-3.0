package travelguideapp.ge.travelguide.ui.home.profile.follow;

import travelguideapp.ge.travelguide.model.response.FollowResponse;

interface FollowFragmentListener {

    void onGetFollowData(Object object);

    void onChooseUser(int userId);

    void onFollowAction(int userId, int position);

    void onFollowActionDone(FollowResponse followResponse);

    void onError(String message);

}
