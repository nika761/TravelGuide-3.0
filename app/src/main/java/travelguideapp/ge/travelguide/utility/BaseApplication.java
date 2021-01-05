package travelguideapp.ge.travelguide.utility;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.os.Build;
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
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static final int SHARING_INTENT_REQUEST_CODE = 500;

    public static long POST_VIEW_TIME;

    public static int CROP_OPTION_X;
    public static int CROP_OPTION_Y;
    public static int AGE_RESTRICTION;
    public static int POST_PER_PAGE_SIZE;
    public static int ITEM_WIDTH_FOR_POSTS;

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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null)
                manager.createNotificationChannel(channel);
        }
    }

    public void showNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_uploading);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_uploading);

        Intent clickIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent, 0);
//        collapsedView.setTextViewText(R.id.text_view_collapsed_1, "Hello World!");
//        collapsedView.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.main_icon_sun)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        notificationManager.notify(1, notification);
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

    public static void shareContent(Activity activity, String content) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        activity.startActivityForResult(sharingIntent, SHARING_INTENT_REQUEST_CODE);
    }
}
