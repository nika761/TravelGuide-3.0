package travelguideapp.ge.travelguide.helper;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import java.util.Locale;
import java.util.Objects;

import travelguideapp.ge.travelguide.base.BaseApplication;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SystemManager {

    public static final int READ_EXTERNAL_STORAGE = 111;
    public static final int WRITE_EXTERNAL_STORAGE = 112;
    public static final int CAMERA = 113;

    public static boolean isReadStoragePermission(Activity activity) {
        boolean permissionGranted = false;
        int result = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        }
        return permissionGranted;
    }

    public static void requestReadStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
    }

    public static boolean isWriteStoragePermission(Activity activity) {
        boolean permissionGranted = false;
        int result = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        }
        return permissionGranted;
    }

    public static void requestWriteStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
    }

    public static boolean isCameraPermission(Activity activity) {
        boolean permissionGranted = false;
        int result = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        }
        return permissionGranted;
    }

    public static void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA);
    }

    public static boolean checkNetworkConnection(Context context) {
        boolean wifiConnected = false;
        boolean mobileDataConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileDataConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return wifiConnected || mobileDataConnected;
    }

    public static void updateCurrentLanguage(Context context) {
        final Application app = ((Application) context.getApplicationContext());
        updateConfig(app);
    }

    public static void updateConfig(Application app) {
        Locale locale = new Locale(GlobalPreferences.getLanguage(app.getBaseContext()));
        Locale.setDefault(locale);
        Configuration config = new Configuration(app.getBaseContext().getResources().getConfiguration());
        config.locale = locale;
        Resources resources = app.getBaseContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static void setLanguage(Context context) {
        Locale locale = new Locale(GlobalPreferences.getLanguage(context));
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }


}
