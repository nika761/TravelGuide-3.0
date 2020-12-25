package travelguideapp.ge.travelguide.ui.home.profile;

import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface ProfileFragmentListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onAuthError(String message);

    void onGetError(String message);

}
