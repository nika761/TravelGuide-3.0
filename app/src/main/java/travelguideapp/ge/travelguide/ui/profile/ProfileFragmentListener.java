package travelguideapp.ge.travelguide.ui.profile;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface ProfileFragmentListener extends BaseListener {
    void onGetProfileInfo(ProfileResponse.Userinfo userInfo);
}
