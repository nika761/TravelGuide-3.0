package travelguideapp.ge.travelguide.base;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Typeface;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

/**
 * Created by n.butskhrikidze on 01/06/2020.
 * <p>
 * დასაბამიდან იყო სიტყვა, და სიტყვა იყო ღმერთთან და ღმერთი იყო სიტყვა.
 * In the beginning was the Word, and the Word was with God, and the Word was God.
 * <p>
 */

public class MainApplication extends Application {

    public static final String CHANNEL_ID = "uploading";
    public static final String CHANNEL_NAME = "upload_post";
    public static int AGE_RESTRICTION;
    public static int ITEM_WIDTH_FOR_POSTS;

    private static String accessToken;

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalPreferences.init(this);
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

}
