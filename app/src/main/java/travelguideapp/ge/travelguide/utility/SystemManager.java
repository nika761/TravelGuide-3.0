package travelguideapp.ge.travelguide.utility;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;
import java.util.Objects;

import travelguideapp.ge.travelguide.utility.ContextUtils;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SystemManager {

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
        Locale locale = new Locale(GlobalPreferences.getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration(app.getBaseContext().getResources().getConfiguration());
        config.locale = locale;
        Resources resources = app.getBaseContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

//    public static void setLanguage(Context context) {
//        Locale locale = new Locale(GlobalPreferences.getLanguage(context));
//        Resources resources = context.getResources();
//        Configuration config = resources.getConfiguration();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//        config.locale = locale;
//        resources.updateConfiguration(config, displayMetrics);
//        ((Activity) context).onConfigurationChanged(config);
//    }

    public static ContextWrapper updateLocale(Context context, Locale localeToSwitchTo) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration(); // 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(localeToSwitchTo); // 2
            LocaleList.setDefault(localeList); // 3
            configuration.setLocales(localeList); // 4
        } else {
            configuration.locale = localeToSwitchTo; // 5
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context = context.createConfigurationContext(configuration); // 6
        } else {
            resources.updateConfiguration(configuration, resources.getDisplayMetrics()); // 7
        }

        return new ContextUtils(context);
    }

}
