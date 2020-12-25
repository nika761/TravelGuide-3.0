package travelguideapp.ge.travelguide.ui.login.signIn;

import travelguideapp.ge.travelguide.model.response.AuthWithFirebaseResponse;
import travelguideapp.ge.travelguide.model.response.LoginResponse;
import travelguideapp.ge.travelguide.model.response.VerifyEmailResponse;

public interface SignInListener {

    void onVerify(VerifyEmailResponse verifyEmailResponse);

    void onSign(LoginResponse loginResponse);

    void onError(String message);

    void onFireBaseAuthSignIn(AuthWithFirebaseResponse authWithFirebaseResponse);

    void onFireBaseSignUp(String token, int platform, String name);

    void onFireBaseAuthSignUp();

}
