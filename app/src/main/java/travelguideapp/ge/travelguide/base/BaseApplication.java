package travelguideapp.ge.travelguide.base;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import travelguideapp.ge.travelguide.R;

import travelguideapp.ge.travelguide.helper.broadcast.NotificationReceiver;
import travelguideapp.ge.travelguide.model.response.AppSettingsResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.ui.language.LanguageActivity;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

/**
 * Created by n.butskhrikidze on 01/07/2020.
 * <p>
 * დასაბამიდან იყო სიტყვა, და სიტყვა იყო ღმერთთან და ღმერთი იყო სიტყვა.
 * In the beginning was the Word, and the Word was with God, and the Word was God.
 * <p>
 */

public class BaseApplication extends Application {

    public static final String CHANNEL_ID = "uploading";
    public static final String CHANNEL_NAME = "upload_post";

    public static long POST_VIEW_TIME;

    public static int CROP_OPTION_X;
    public static int CROP_OPTION_Y;
    public static int AGE_RESTRICTION;
    public static int POST_PER_PAGE_SIZE;
    public static int ITEM_WIDTH_FOR_POSTS;
    public static int APP_VERSION = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            overrideFont("SERIF", "font/roboto_thin.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAppSettings();
    }

    private void setFirstUse() {
        try {
            GlobalPreferences.saveIsFirstUse(getApplicationContext(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null)
                manager.createNotificationChannel(channel);
        }
    }

    public void overrideFont(String defaultFontNameToOverride, String customFontFileNameInAssets) {

        final Typeface customFontTypeface = Typeface.createFromAsset(getAssets(), customFontFileNameInAssets);

        Map<String, Typeface> newMap = new HashMap<>();
        newMap.put("roboto_thin", customFontTypeface);
        try {
            final Field staticField = Typeface.class.getDeclaredField("sSystemFontMap");
            staticField.setAccessible(true);
            staticField.set(null, newMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }




    public void getAppSettings() {
        try {
            ApiService apiService = RetrofitManager.getApiService();
            apiService.getAppSettings().enqueue(new Callback<AppSettingsResponse>() {
                @Override
                public void onResponse(Call<AppSettingsResponse> call, Response<AppSettingsResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CROP_OPTION_X = response.body().getApp_settings().getStory_photo_crop_width();
                        CROP_OPTION_Y = response.body().getApp_settings().getStory_photo_crop_height();
                        AGE_RESTRICTION = response.body().getApp_settings().getAge_restriction();
                        POST_PER_PAGE_SIZE = response.body().getApp_settings().getProfile_posts_quntity_per_page();
                        try {
                            POST_VIEW_TIME = (long) response.body().getApp_settings().getStory_view_deley_duration();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (GlobalPreferences.getIseFirstUse(getApplicationContext())) {
                            setFirstUse();
                            GlobalPreferences.saveAppVersion(getApplicationContext(), response.body().getApp_settings().getAndroid_version());
                        }
                        APP_VERSION = response.body().getApp_settings().getAndroid_version();
                    }
                }

                @Override
                public void onFailure(Call<AppSettingsResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
