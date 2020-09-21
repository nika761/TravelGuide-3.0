package com.example.travelguide.ui.home.profile.userFollowActivity.following;

import com.example.travelguide.model.response.FollowingResponse;

public interface FollowingFragmentListener {

    void onGetFollowing(FollowingResponse followingResponse);

    void onGetFollowingError(String message);

}
