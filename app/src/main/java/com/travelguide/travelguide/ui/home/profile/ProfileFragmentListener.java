package com.travelguide.travelguide.ui.home.profile;

import com.travelguide.travelguide.model.response.ProfileResponse;

public interface ProfileFragmentListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onGetError(String message);

}
