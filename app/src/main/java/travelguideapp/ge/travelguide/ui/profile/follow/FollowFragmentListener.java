package travelguideapp.ge.travelguide.ui.profile.follow;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.FollowResponse;

interface FollowFragmentListener extends BaseViewListener {

    void onGetFollowData(Object object);

    void onChooseUser(int userId);

    void onStartFollowing(int userId, int position);

    void onStopFollowing(String userName, int userId, int position);

    void onFollowActionDone(FollowResponse followResponse);

}
