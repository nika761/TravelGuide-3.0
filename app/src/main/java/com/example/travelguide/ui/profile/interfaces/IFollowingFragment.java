package com.example.travelguide.ui.profile.interfaces;

import com.example.travelguide.model.response.FollowingResponse;

public interface IFollowingFragment {

    void onGetFollowing(FollowingResponse followingResponse);
    void onGetFollowingError(String message);

}
