package com.travelguide.travelguide.ui.home.profile.userFollowActivity.following;

import com.travelguide.travelguide.model.response.FollowingResponse;

public interface FollowingFragmentListener {

    void onGetFollowing(FollowingResponse followingResponse);

    void onGetFollowingError(String message);

}
