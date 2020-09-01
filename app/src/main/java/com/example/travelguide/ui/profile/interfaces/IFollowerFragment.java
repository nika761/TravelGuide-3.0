package com.example.travelguide.ui.profile.interfaces;

import com.example.travelguide.model.request.FollowersRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.FollowerResponse;

public interface IFollowerFragment {
    void onGetFollowers(FollowerResponse followerResponse);

    void onGetFollowersError(String message);

    void onFollowStart(int userId);

    void onFollowSuccess(FollowResponse followResponse);

    void onFollowError(String message);
}
