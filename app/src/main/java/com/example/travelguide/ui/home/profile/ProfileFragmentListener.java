package com.example.travelguide.ui.home.profile;

import com.example.travelguide.model.response.ProfileResponse;

public interface ProfileFragmentListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onGetError(String message);

}
