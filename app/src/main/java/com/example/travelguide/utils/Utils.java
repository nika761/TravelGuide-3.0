package com.example.travelguide.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.travelguide.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void saveUser(Context context, User user) {
        List<User> users = getSavedUsers(context);
        if (!users.contains(user)) {
            users.add(user);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_list", new Gson().toJson(users));
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_preference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString("user", null), User.class);
        return user;
    }

    public static List<User> getSavedUsers(Context context) {
        List<User> users = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_preference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>() {
        }.getType();
        if (sharedPreferences.getString("user_list", null) != null) {
            users = gson.fromJson(sharedPreferences.getString("user_list", null), listType);
        }
        return users;
    }

    public static void deleteUser(Context context, User user) {
        List<User> users = getSavedUsers(context);
        users.remove(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_list", new Gson().toJson(users));
        editor.apply();
    }
}
