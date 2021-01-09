package travelguideapp.ge.travelguide.ui.home.profile;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface ProfileFragmentListener extends BaseListener {

    void onGetProfile(ProfileResponse.Userinfo userInfo);

}
