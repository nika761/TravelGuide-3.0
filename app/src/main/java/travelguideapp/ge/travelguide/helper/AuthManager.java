package travelguideapp.ge.travelguide.helper;

import android.app.Activity;

import travelguideapp.ge.travelguide.model.customModel.AuthModel;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

public class AuthManager {

    public static void persistAuthState(Activity activity, AuthModel authModel) {
        new Thread(() -> {
            try {
                GlobalPreferences.saveAccessToken(activity, authModel.getAccessToken());
                GlobalPreferences.saveUserId(activity, authModel.getUserId());
                GlobalPreferences.saveUserRole(activity, authModel.getUserRole());
                GlobalPreferences.saveLoginType(activity, authModel.getLoginType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void resetAuthState(Activity activity) {
        new Thread(() -> {
            try {
                GlobalPreferences.saveAccessToken(activity, null);
                GlobalPreferences.saveUserId(activity, 0);
                GlobalPreferences.saveUserRole(activity, 20);
                GlobalPreferences.saveLoginType(activity, null);
                GlobalPreferences.saveUserProfileInfo(activity, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
