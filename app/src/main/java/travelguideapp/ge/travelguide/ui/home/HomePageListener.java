package travelguideapp.ge.travelguide.ui.home;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface HomePageListener extends BaseViewListener {
    void onGetProfile(ProfileResponse.Userinfo userInfo);
}
