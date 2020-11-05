package com.travel.guide.ui.home.profile.editProfile;

import com.travel.guide.model.response.ProfileResponse;

public interface ProfileEditListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onGetError(String message);

}
