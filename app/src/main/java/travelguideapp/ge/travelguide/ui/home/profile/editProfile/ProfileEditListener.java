package travelguideapp.ge.travelguide.ui.home.profile.editProfile;

import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.model.response.UpdateProfileResponse;

public interface ProfileEditListener {

    void onGetProfileInfo(ProfileResponse.Userinfo userInfo);

    void onUpdateSuccess(UpdateProfileResponse updateProfileResponse);

    void onPhotoUploadedToS3();

    void onError(String message);

}
