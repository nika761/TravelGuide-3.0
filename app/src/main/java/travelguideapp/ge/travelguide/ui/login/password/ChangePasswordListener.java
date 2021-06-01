package travelguideapp.ge.travelguide.ui.login.password;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.ChangePasswordResponse;

public interface ChangePasswordListener extends BaseViewListener {
    void onChangePasswordResponse(ChangePasswordResponse changePasswordResponse);
}
