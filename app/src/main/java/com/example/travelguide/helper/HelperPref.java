package com.example.travelguide.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.travelguide.model.User;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.model.response.ProfileResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HelperPref {
    private static final String LANGUAGE_PREFERENCES = "language_preference";
    private static final String LANGUAGE_KEY = "language_Id";

    private static final String USER_PREFERENCES = "user_preference";
    private static final String USER_KEY = "user_list";

    public static final String FACEBOOK = "facebook";
    public static final String GOOGLE = "google";

    private static final String SERVER_USER_PREFERENCES = "server_user_preference";
    private static final String SERVER_USER_KEY = "server_user_list";

    private static final String LOGGED_USER_PREFERENCES = "logged_user_preference";
    private static final String LOGGED_USER_KEY = "logged_user";

    private static final String ACCESS_TOKEN_PREFERENCES = "access_preference";
    private static final String ACCESS_KEY = "access_token";

    private static final String USER_PROFILE_PREFERENCES = "user_profile_prefs";
    private static final String PROFILE_KEY = "user_profile";

    public static void saveUserProfileInfo(Context context, ProfileResponseModel.Userinfo userinfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_KEY, new Gson().toJson(userinfo));
        editor.apply();
    }

    public static ProfileResponseModel.Userinfo getUserProfileInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PROFILE_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ProfileResponseModel.Userinfo currentUserInfo = gson.fromJson(sharedPreferences.getString(PROFILE_KEY, null),
                ProfileResponseModel.Userinfo.class);
        return currentUserInfo;
    }

    public static void saveUser(Context context, User user) {
        List<User> users = getSavedUsers(context);
        if (!users.contains(user)) {
            users.add(user);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, new Gson().toJson(users));
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString(USER_KEY, null), User.class);
        return user;
    }


    public static void deleteUser(Context context, User user) {
        List<User> users = getSavedUsers(context);
        users.remove(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, new Gson().toJson(users));
        editor.apply();
    }


    public static List<User> getSavedUsers(Context context) {
        List<User> users = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>() {
        }.getType();
        if (sharedPreferences.getString(USER_KEY, null) != null) {
            users = gson.fromJson(sharedPreferences.getString(USER_KEY, null), listType);
        }
        return users;
    }

    public static void saveServerUser(Context context, LoginResponseModel.User user) {
        List<LoginResponseModel.User> serverSavedUsers = getServerSavedUsers(context);
        if (!serverSavedUsers.contains(user)) {
            serverSavedUsers.add(user);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVER_USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SERVER_USER_KEY, new Gson().toJson(serverSavedUsers));
        editor.apply();
    }

    public static List<LoginResponseModel.User> getServerSavedUsers(Context context) {
        List<LoginResponseModel.User> users = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVER_USER_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<LoginResponseModel.User>>() {
        }.getType();
        if (sharedPreferences.getString(SERVER_USER_KEY, null) != null) {
            users = gson.fromJson(sharedPreferences.getString(SERVER_USER_KEY, null), listType);
        }
        return users;
    }

    public static LoginResponseModel.User getCurrentLoggedUser(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGGED_USER_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        return gson.fromJson(sharedPreferences.getString(LOGGED_USER_KEY, null), LoginResponseModel.User.class);
    }

    public static void deleteServerUser(Context context, LoginResponseModel.User user) {
        List<LoginResponseModel.User> users = getServerSavedUsers(context);
        users.remove(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVER_USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SERVER_USER_KEY, new Gson().toJson(users));
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

    public static String getCurrentAccessToken(Context context) {
        String accessToken = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACCESS_TOKEN_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(ACCESS_KEY, null) != null) {
            accessToken = sharedPreferences.getString(ACCESS_KEY, null);
        }
        return accessToken;
    }


}
