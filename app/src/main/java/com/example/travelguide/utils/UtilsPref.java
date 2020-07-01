package com.example.travelguide.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.travelguide.model.User;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UtilsPref {

    private static final String LANGUAGE_KEY = "language_Id";
    private static final String LANGUAGE_PREFERENCES = "language_preference";
    private static final String USER_PREFERENCES = "user_preference";
    private static final String USER_KEY = "user_list";
    public static final String FACEBOOK = "facebook";
    public static final String GOOGLE = "google";


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

    public static void deleteUser(Context context, User user) {
        List<User> users = getSavedUsers(context);
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


}
