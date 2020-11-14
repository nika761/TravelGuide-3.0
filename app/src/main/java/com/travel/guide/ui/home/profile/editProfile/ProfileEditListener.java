package com.travel.guide.ui.home.profile.editProfile;

import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.model.response.UpdateProfileResponse;

public interface ProfileEditListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onUpdateProfile(UpdateProfileResponse updateProfileResponse);

    void onGetError(String message);

}
