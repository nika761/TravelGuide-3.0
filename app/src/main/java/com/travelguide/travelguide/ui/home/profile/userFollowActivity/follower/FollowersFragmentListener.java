package com.travelguide.travelguide.ui.home.profile.userFollowActivity.follower;

import com.travelguide.travelguide.model.response.FollowResponse;
import com.travelguide.travelguide.model.response.FollowerResponse;

public interface FollowersFragmentListener {
    void onGetFollowers(FollowerResponse followerResponse);

    void onGetFollowersError(String message);

    void onFollowStart(int userId);

    void onFollowSuccess(FollowResponse followResponse);

    void onFollowError(String message);
}
