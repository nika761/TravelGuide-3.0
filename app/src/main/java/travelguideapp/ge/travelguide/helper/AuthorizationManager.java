package travelguideapp.ge.travelguide.helper;

import android.app.Activity;

import com.facebook.login.LoginManager;

import travelguideapp.ge.travelguide.model.customModel.AuthModel;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

public class AuthorizationManager {

    public static void persistAuthorizationState(AuthModel authModel) {
        new Thread(() -> {
            try {
                GlobalPreferences.setAccessToken(authModel.getAccessToken());
                GlobalPreferences.setUserId(authModel.getUserId());
                GlobalPreferences.setUserRole(authModel.getUserRole());
                GlobalPreferences.setLoginType(authModel.getLoginType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void resetAuthorizationState(Activity activity) throws Exception {
        new Thread(() -> {
            try {
                GlobalPreferences.setAccessToken("");
                GlobalPreferences.setUserId(0);
                GlobalPreferences.setUserRole(20);
                GlobalPreferences.setLoginType("");
                GlobalPreferences.setUserProfileInfo("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            activity.startActivity(SignInActivity.getRedirectIntent(activity));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void logOut(Activity activity) {
        try {
            String loginType = GlobalPreferences.getLoginType();
            switch (loginType) {
                case GlobalPreferences.FACEBOOK:
                    logOutFromFacebook();
                    break;

                case GlobalPreferences.GOOGLE:
                    logOutFromGoogle(activity);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            resetAuthorizationState(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void logOutFromGoogle(Activity activity) {
        try {
            ClientManager.googleSignInClient(activity).signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
//                .addOnCompleteListener(activity, null)
//                .addOnCanceledListener(activity, null)
//                .addOnFailureListener(activity, null);
    }


    private static void logOutFromFacebook() {
        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
