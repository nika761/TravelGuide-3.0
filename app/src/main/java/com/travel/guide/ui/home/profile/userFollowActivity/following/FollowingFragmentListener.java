package com.travel.guide.ui.home.profile.userFollowActivity.following;

import com.travel.guide.model.response.FollowingResponse;

public interface FollowingFragmentListener {

    void onGetFollowing(FollowingResponse followingResponse);

    void onGetFollowingError(String message);

}
