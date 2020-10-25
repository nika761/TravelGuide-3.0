package com.example.travelguide.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.travelguide.model.User;
import com.example.travelguide.model.UserModel;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.model.response.ProfileResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HelperPref {
    private static final String LANGUAGE_PREFERENCES = "language_preference";
    private static final String LANGUAGE_KEY = "language_Id";

    private static final String USER_PREFERENCES = "users_preference";
    private static final String USER_KEY = "users";

    public static final String FACEBOOK = "facebook";
    public static final String GOOGLE = "google";
    public static final String TRAVEL_GUIDE = "travel_guide";

    private static final String LOGGED_USER_PREFERENCES = "logged_user_preference";
    private static final String LOGGED_USER_KEY = "logged_user";

    private static final String ACCESS_TOKEN_PREFERENCES = "access_preference";
    private static final String ACCESS_KEY = "access_token";

    private static final String USER_PROFILE_PREFERENCES = "user_profile_prefs";
    private static final String PROFILE_KEY = "user_profile";

    private static final String USER_ID_PREF = "user_id_prefs";
    private static final String USER_ID_KEY = "user_id";

    private static final String PLATFORM_PREF = "platform_pref";
    private static final String PLATFORM_KEY = "platform_key";

//    public enum LoginTypeEnum {
//        GOOGLE, FACEBOOK, TRAVEL_GUIDE
//    }


    public static void saveUserProfileInfo(Context context, ProfileResponse.Userinfo userinfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_KEY, new Gson().toJson(userinfo));
        editor.apply();
    }

    public static ProfileResponse.Userinfo getUserProfileInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString(PROFILE_KEY, null),
                ProfileResponse.Userinfo.class);
    }

    public static void saveCurrentUserId(Context context, int userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_ID_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    public static int getCurrentUserId(Context context) {
        int userId = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_ID_PREF, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(USER_ID_KEY, 0) != 0) {
            userId = sharedPreferences.getInt(USER_ID_KEY, 0);
        }
        return userId;
    }

    public static void saveCurrentPlatform(Context context, String platform) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PLATFORM_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PLATFORM_KEY, platform);
        editor.apply();
    }

    public static String getCurrentPlatform(Context context) {
        String platform = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PLATFORM_PREF, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(PLATFORM_KEY, null) != null) {
            platform = sharedPreferences.getString(PLATFORM_KEY, null);
        }
        return platform;
    }


    public static void saveUser(Context context, LoginResponse.User user) {
        List<LoginResponse.User> users = getSavedUsers(context);
        if (!users.contains(user)) {
            users.add(user);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, new Gson().toJson(users));
        editor.apply();
    }

    public static void saveCurrentUser(Context context, LoginResponse.User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGGED_USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGGED_USER_KEY, new Gson().toJson(user));
        editor.apply();
    }

    public static LoginResponse.User getCurrentUser(Context context) {
        LoginResponse.User user;
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGGED_USER_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<LoginResponse.User>() {
        }.getType();
        if (sharedPreferences.getString(LOGGED_USER_KEY, null) != null) {
            user = gson.fromJson(sharedPreferences.getString(LOGGED_USER_KEY, null), listType);
            return user;
        }
        return null;
    }


    public static List<LoginResponse.User> getSavedUsers(Context context) {
        List<LoginResponse.User> users = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<LoginResponse.User>>() {
        }.getType();
        if (sharedPreferences.getString(USER_KEY, null) != null) {
            users = gson.fromJson(sharedPreferences.getString(USER_KEY, null), listType);
        }
        return users;
    }

    public static void deleteUser(Context context, LoginResponse.User user) {
        List<LoginResponse.User> users = getSavedUsers(context);
        users.remove(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, new Gson().toJson(users));
        editor.apply();
    }

    public static void saveLanguageId(Context context, int languageId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LANGUAGE_KEY, languageId);
        editor.apply();
    }

    public static int getLanguageId(Context context) {
        int languageId = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(LANGUAGE_KEY, 0) != 0) {
            languageId = sharedPreferences.getInt(LANGUAGE_KEY, 0);
        }
        return languageId;
    }

    public static void saveAccessToken(Context context, String accessToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACCESS_TOKEN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_KEY, accessToken);
        editor.apply();
    }

    public static String getAccessToken(Context context) {
        String accessToken = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACCESS_TOKEN_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(ACCESS_KEY, null) != null) {
            accessToken = sharedPreferences.getString(ACCESS_KEY, null);
        }
        return accessToken;
    }


}
