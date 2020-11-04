package com.travelguide.travelguide.ui.home.profile.editProfile;

import com.travelguide.travelguide.model.response.ProfileResponse;

public interface ProfileEditListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onGetError(String message);

}
