package travelguideapp.ge.travelguide.ui.home.customerUser;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface CustomerProfileListener extends BaseViewListener {

    void onGetProfile(ProfileResponse profileResponse);

    void onFollowSuccess(FollowResponse followResponse);

}
