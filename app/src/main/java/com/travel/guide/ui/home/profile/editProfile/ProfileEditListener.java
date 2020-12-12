package com.travel.guide.ui.home.profile.editProfile;

import com.travel.guide.enums.UserInfoFields;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.model.response.UpdateProfileResponse;

import java.util.HashMap;
import java.util.List;

public interface ProfileEditListener {

    void onGetProfileInfo(ProfileResponse.Userinfo userInfo);

    void onUpdateSuccess(UpdateProfileResponse updateProfileResponse);

    void onPhotoUploadedToS3();

    void onError(String message);

}
