package travelguideapp.ge.travelguide.ui.home;

import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface HomePageListener {
    void onGetProfile(ProfileResponse.Userinfo userInfo);

    void onAuthError(String s);
}
