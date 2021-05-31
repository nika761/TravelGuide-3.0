package travelguideapp.ge.travelguide.ui.home;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface HomePageListener extends BaseListener {
    void onGetProfile(ProfileResponse.Userinfo userInfo);
}
