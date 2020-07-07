package com.example.travelguide.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class UtilsPermissions {

    public static final int READ_EXTERNAL_STORAGE = 111;
    private final int WRITE_EXTERNAL_STORAGE = 222;

    public static boolean isExStoragePermissionGranted(Activity activity) {
        boolean permissionGranted = false;
        int result = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        }
        return permissionGranted;
    }

    public static void requestExStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
    }
}
