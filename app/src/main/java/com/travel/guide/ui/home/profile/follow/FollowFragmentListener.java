package com.travel.guide.ui.home.profile.follow;

import com.travel.guide.model.response.FollowResponse;

interface FollowFragmentListener {

    void onGetFollowData(Object object);

    void onFollowAction(int userId, int position);

    void onFollowActionDone(FollowResponse followResponse);

    void onError(String message);

}
