package com.example.travelguide.ui.customerUser;

import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.ProfileResponse;

public interface CustomerProfileListener {
    void onGetProfile(ProfileResponse profileResponse);

    void onGerProfileError(String message);

    void onFollowSuccess(FollowResponse followResponse);

    void onFollowError(String message);
}
