package travelguideapp.ge.travelguide.ui.login.password;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;

public interface ChangePasswordListener extends BaseListener {
    void onChangePasswordResponse(ChangePasswordResponse changePasswordResponse);
}
