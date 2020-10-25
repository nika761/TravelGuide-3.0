package com.example.travelguide.ui.home.profile.editProfile;

import com.example.travelguide.model.response.ProfileResponse;

public interface ProfileEditListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onGetError(String message);

}
