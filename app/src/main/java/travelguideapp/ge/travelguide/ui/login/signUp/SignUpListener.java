package travelguideapp.ge.travelguide.ui.login.signUp;

import travelguideapp.ge.travelguide.model.response.SignUpResponse;
import travelguideapp.ge.travelguide.model.response.CheckNickResponse;

public interface SignUpListener {

    void onSignUpResponse(SignUpResponse signUpResponse);

    void onError(String message);

    void onPhotoUploadToS3();

//    void onGetEmailCheckResult(CheckMailResponse checkMailResponse);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

}
