package travelguideapp.ge.travelguide.ui.login.signUp;

import travelguideapp.ge.travelguide.model.response.CheckNickResponse;
import travelguideapp.ge.travelguide.model.response.SignUpWithFirebaseResponse;

public interface SignUpFireBaseListener {

    void onSuccess(SignUpWithFirebaseResponse signUpWithFirebaseResponse);

    void onError(String message);

    void onGetNickCheckResult(CheckNickResponse checkNickResponse);

    void checkNickName();

}
