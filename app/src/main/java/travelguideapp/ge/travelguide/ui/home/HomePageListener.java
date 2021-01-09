package travelguideapp.ge.travelguide.ui.home;

import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.base.BaseListener;

public interface HomePageListener extends BaseListener {
    void onGetProfile(ProfileResponse.Userinfo userInfo);
}
