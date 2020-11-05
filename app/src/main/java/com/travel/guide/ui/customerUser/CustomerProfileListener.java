package com.travel.guide.ui.customerUser;

import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.ProfileResponse;

public interface CustomerProfileListener {

    void onGetProfile(ProfileResponse profileResponse);

    void onError(String message);

    void onFollowSuccess(FollowResponse followResponse);

}
