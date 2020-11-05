package com.travel.guide.ui.home.profile.userFollowActivity.follower;

import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.FollowerResponse;

public interface FollowersFragmentListener {
    void onGetFollowers(FollowerResponse followerResponse);

    void onGetFollowersError(String message);

    void onFollowStart(int userId);

    void onFollowSuccess(FollowResponse followResponse);

    void onFollowError(String message);
}
