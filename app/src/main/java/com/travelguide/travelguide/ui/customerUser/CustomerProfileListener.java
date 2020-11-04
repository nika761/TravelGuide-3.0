package com.travelguide.travelguide.ui.customerUser;

import com.travelguide.travelguide.model.response.FollowResponse;
import com.travelguide.travelguide.model.response.ProfileResponse;

public interface CustomerProfileListener {

    void onGetProfile(ProfileResponse profileResponse);

    void onError(String message);

    void onFollowSuccess(FollowResponse followResponse);

}
