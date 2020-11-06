package com.travel.guide.ui.home.profile.follow;

import com.travel.guide.model.response.FollowResponse;

interface FollowFragmentListener {

    void onGetFollowData(Object object);

    void onFollowChangeRequest(int userId, int position);

    void onFollowChanged(FollowResponse followResponse);

    void onError(String message);

}
