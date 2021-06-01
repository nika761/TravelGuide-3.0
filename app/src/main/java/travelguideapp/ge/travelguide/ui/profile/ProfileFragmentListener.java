package travelguideapp.ge.travelguide.ui.profile;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface ProfileFragmentListener extends BaseViewListener {
    void onGetProfileInfo(ProfileResponse.Userinfo userInfo);
}
