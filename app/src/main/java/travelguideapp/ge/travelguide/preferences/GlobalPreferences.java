package travelguideapp.ge.travelguide.preferences;

import android.app.Activity;
import android.content.Context;

import static travelguideapp.ge.travelguide.network.ApiConstants.ACCESS_TOKEN_BEARER;

public class GlobalPreferences {

    public static final String FACEBOOK = "facebook";
    public static final String GOOGLE = "google";
    public static final String TRAVEL_GUIDE = "travel_guide";

    private static final String LANGUAGE_PREFERENCES = "LANGUAGE_PREFERENCES";
    private static final String GLOBAL_LANGUAGE_KEY = "GLOBAL_LANGUAGE_KEY";

    private static final String TRAVEL_GUIDE_PREFERENCES = "travel_guide_preference";
    private static final String USER_PROFILE_INFO = "user_profile";
    private static final String APP_SETTINGS = "app_settings_key";
    private static final String SIGNED_PLATFORM = "platform_key";
    private static final String APP_VERSION = "app_version_key";
    private static final String LOGGED_USER_KEY = "logged_user";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String LANGUAGE_LOCALE = "language";
    private static final String LANGUAGE_ID = "language_Id";
    private static final String USER_ROLE = "user_role";
    private static final String FIRST_RUN = "first_use";
    private static final String USER_ID = "user_id";
    private static final String USER_KEY = "users";

    private static ObscuredSharedPreferences sharedPreferences;

    private static String userProfileInfo;
    private static String appSettings;
    private static String accessToken;
    private static String loginType;
    private static String language;
    private static int languageId;
    private static int appVersion;
    private static int userRole;
    private static int userId;

    public static void init(Context context) {
        sharedPreferences = ObscuredSharedPreferences.getPrefs(context, TRAVEL_GUIDE_PREFERENCES, Activity.MODE_PRIVATE);
        try {
            userProfileInfo = sharedPreferences.getString(USER_PROFILE_INFO, "");
            loginType = sharedPreferences.getString(SIGNED_PLATFORM, "");
            appSettings = sharedPreferences.getString(APP_SETTINGS, "");
            accessToken = sharedPreferences.getString(ACCESS_TOKEN, "");
            language = sharedPreferences.getString(LANGUAGE_LOCALE, "");
            languageId = sharedPreferences.getInt(LANGUAGE_ID, -1);
            appVersion = sharedPreferences.getInt(APP_VERSION, -1);
            userRole = sharedPreferences.getInt(USER_ROLE, -1);
            userId = sharedPreferences.getInt(USER_ID, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAccessToken(String accessToken) {
        try {
            if (!accessToken.equals("")) {
                accessToken = ACCESS_TOKEN_BEARER + accessToken;
            }
            GlobalPreferences.accessToken = accessToken;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ACCESS_TOKEN, accessToken);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setUserProfileInfo(String userProfileInfo) {
        try {
            GlobalPreferences.userProfileInfo = userProfileInfo;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_PROFILE_INFO, userProfileInfo);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUserProfileInfo() {
        return userProfileInfo;
    }

    public static String getAppSettings() {
        return appSettings;
    }

    public static void setAppSettings(String appSettings) {
        try {
            GlobalPreferences.appSettings = appSettings;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APP_SETTINGS, appSettings);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLoginType() {
        return loginType;
    }

    public static void setLoginType(String loginType) {
        try {
            GlobalPreferences.loginType = loginType;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SIGNED_PLATFORM, loginType);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        try {
            GlobalPreferences.language = language;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LANGUAGE_LOCALE, language);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getLanguageId() {
        return languageId;
    }

    public static void setLanguageId(int languageId) {
        try {
            GlobalPreferences.languageId = languageId;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(LANGUAGE_ID, languageId);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getAppVersion() {
        return appVersion;
    }

    public static void setAppVersion(int appVersion) {
        try {
            GlobalPreferences.appVersion = appVersion;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(APP_VERSION, appVersion);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getUserRole() {
        return userRole;
    }

    public static void setUserRole(int userRole) {
        try {
            GlobalPreferences.userRole = userRole;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(USER_ROLE, userRole);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        try {
            GlobalPreferences.userId = userId;
            ObscuredSharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(USER_ID, userId);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private static SharedPreferences getPref(Context context) {
//        return context.getSharedPreferences(TRAVEL_GUIDE_PREFERENCES, Context.MODE_PRIVATE);
//    }
//
//    private static SharedPreferences getLanguagePref(Context context) {
//        return context.getSharedPreferences(LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
//    }
//
//    /// User Role
//
//    public static void saveUserRole(Context context, int userRole) {
//        getPref(context).edit().putInt(USER_ROLE, userRole).apply();
//    }
//
//    public static void removeUserRole(Context context) {
//        getPref(context).edit().remove(USER_ROLE).apply();
//    }
//
//    public static int getUserRole(Context context) {
//        int userRole = 20;
//        if (getPref(context).getInt(USER_ROLE, 20) != 20) {
//            userRole = getPref(context).getInt(USER_ROLE, 20);
//        }
//        return userRole;
//    }
//
//    /// User Id
//
//    public static void saveUserId(Context context, int userId) {
//        getPref(context).edit().putInt(USER_ID, userId).apply();
//    }
//
//    public static void removeUserId(Context context) {
//        getPref(context).edit().remove(USER_ID).apply();
//    }
//
//    public static int getUserId(Context context) {
//        int userId = 0;
//        if (getPref(context).getInt(USER_ID, 0) != 0) {
//            userId = getPref(context).getInt(USER_ID, 0);
//        }
//        return userId;
//    }
//
//    ///Login Type
//
//    public static void saveLoginType(Context context, String platform) {
//        getPref(context).edit().putString(SIGNED_PLATFORM, platform).apply();
//    }
//
//    public static void removeLoginType(Context context) {
//        getPref(context).edit().remove(SIGNED_PLATFORM).apply();
//    }
//
//    public static String getLoginType(Context context) {
//        String platform = null;
//        if (getPref(context).getString(SIGNED_PLATFORM, null) != null) {
//            platform = getPref(context).getString(SIGNED_PLATFORM, null);
//        }
//        return platform;
//    }
//
//    ///Language Id
//
//    public static void saveLanguageId(Context context, int languageId) {
//        getPref(context).edit().putInt(LANGUAGE_ID, languageId).apply();
//    }
//
//    public static int getLanguageId(Context context) {
//        int languageId = 0;
//        if (getPref(context).getInt(LANGUAGE_ID, 0) != 0) {
//            languageId = getPref(context).getInt(LANGUAGE_ID, 0);
//        }
//        return languageId;
//    }
//
//    public static void saveLanguage(Context context, String language) {
//        getPref(context).edit().putString(LANGUAGE_LOCALE, language).apply();
//    }
//
//    public static String getLanguage(Context context) {
//        String language = "en";
//        if (getPref(context).getString(LANGUAGE_LOCALE, null) != null) {
//            return getPref(context).getString(LANGUAGE_LOCALE, null);
//        } else {
//            return language;
//        }
//    }
//
//    ///AccessToken
//
//    public static void saveAccessToken(Context context, String accessToken) {
//        String access = ACCESS_TOKEN_BEARER + accessToken;
//        getPref(context).edit().putString(ACCESS_TOKEN, access).apply();
//    }
//
//    public static void removeAccessToken(Context context) {
//        getPref(context).edit().remove(ACCESS_TOKEN).apply();
//    }
//
//    public static String getAccessToken(Context context) {
//        String accessToken = null;
//
//        if (getPref(context).getString(ACCESS_TOKEN, null) != null) {
//            accessToken = getPref(context).getString(ACCESS_TOKEN, null);
//        }
//
//        return accessToken;
//    }
//
//
//    public static void saveIsFirstUse(Context context, boolean firsUse) {
//        getPref(context).edit().putBoolean(FIRST_RUN, firsUse).apply();
//    }
//
//
//    public static boolean getIseFirstUse(Context context) {
//        return getPref(context).getBoolean(FIRST_RUN, true);
//    }
//
//
//    public static void saveAppVersion(Context context, int appVersion) {
//        getPref(context).edit().putInt(APP_VERSION, appVersion).apply();
//    }
//
//    public static void removeAppVersion(Context context) {
//        getPref(context).edit().remove(APP_VERSION).apply();
//    }
//
//    public static int getAppVersion(Context context) {
//        int appVersion = 0;
//        if (getPref(context).getInt(APP_VERSION, 0) != 0) {
//            appVersion = getPref(context).getInt(APP_VERSION, 0);
//        }
//        return appVersion;
//    }
//
//    public static void saveCurrentLanguage(Context context, GlobalLanguages globalLanguages) {
//        getLanguagePref(context).edit().putString(GLOBAL_LANGUAGE_KEY, new Gson().toJson(globalLanguages)).apply();
//    }
//
//    public static GlobalLanguages getCurrentLanguage(Context context) {
//        SharedPreferences sharedPreferences = getLanguagePref(context);
//        Gson gson = new Gson();
//        return gson.fromJson(sharedPreferences.getString(GLOBAL_LANGUAGE_KEY, null), GlobalLanguages.class);
//    }
//
//    ///
//
//    public static void saveUser(Context context, LoginResponse.User user) {
//        List<LoginResponse.User> users = getSavedUsers(context);
//        if (!users.contains(user)) {
//            users.add(user);
//        }
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRAVEL_GUIDE_PREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(USER_KEY, new Gson().toJson(users));
//        editor.apply();
//    }
//
//    public static void saveCurrentUser(Context context, LoginResponse.User user) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRAVEL_GUIDE_PREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(LOGGED_USER_KEY, new Gson().toJson(user));
//        editor.apply();
//    }
//
//    public static LoginResponse.User getCurrentUser(Context context) {
//        LoginResponse.User user;
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRAVEL_GUIDE_PREFERENCES, Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        Type listType = new TypeToken<LoginResponse.User>() {
//        }.getType();
//        if (sharedPreferences.getString(LOGGED_USER_KEY, null) != null) {
//            user = gson.fromJson(sharedPreferences.getString(LOGGED_USER_KEY, null), listType);
//            return user;
//        }
//        return null;
//    }
//
//    public static List<LoginResponse.User> getSavedUsers(Context context) {
//        List<LoginResponse.User> users = new ArrayList<>();
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRAVEL_GUIDE_PREFERENCES, Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        Type listType = new TypeToken<List<LoginResponse.User>>() {
//        }.getType();
//        if (sharedPreferences.getString(USER_KEY, null) != null) {
//            users = gson.fromJson(sharedPreferences.getString(USER_KEY, null), listType);
//        }
//        return users;
//    }
//
//    public static void deleteUser(Context context, LoginResponse.User user) {
//        List<LoginResponse.User> users = getSavedUsers(context);
//        users.remove(user);
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRAVEL_GUIDE_PREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(USER_KEY, new Gson().toJson(users));
//        editor.apply();
//    }
//
//    public static void saveAppSettings(Context context, AppSettings appSettings) {
//        getPref(context).edit().putString(APP_SETTINGS, new Gson().toJson(appSettings)).apply();
//    }
//
//    public static AppSettings getAppSettings(Context context) {
//        SharedPreferences sharedPreferences = getPref(context);
//        Gson gson = new Gson();
//        return gson.fromJson(sharedPreferences.getString(APP_SETTINGS, null), AppSettings.class);
//    }
//
//    public static void saveUserProfileInfo(Context context, ProfileResponse.Userinfo userinfo) {
//        getPref(context).edit().putString(USER_PROFILE_INFO, new Gson().toJson(userinfo)).apply();
//    }
//
//    public static void removeUserProfileInfo(Context context) {
//        getPref(context).edit().remove(USER_PROFILE_INFO).apply();
//    }
//
//    public static ProfileResponse.Userinfo getUserProfileInfo(Context context) {
//        SharedPreferences sharedPreferences = getPref(context);
//        Gson gson = new Gson();
//        return gson.fromJson(sharedPreferences.getString(USER_PROFILE_INFO, null), ProfileResponse.Userinfo.class);
//    }

}
