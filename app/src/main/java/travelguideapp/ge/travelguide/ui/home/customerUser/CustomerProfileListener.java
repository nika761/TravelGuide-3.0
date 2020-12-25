package travelguideapp.ge.travelguide.ui.home.customerUser;

import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;

public interface CustomerProfileListener {

    void onGetProfile(ProfileResponse profileResponse);

    void onError(String message);

    void onFollowSuccess(FollowResponse followResponse);

}
