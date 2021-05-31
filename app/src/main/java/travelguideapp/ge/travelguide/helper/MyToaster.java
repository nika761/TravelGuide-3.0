package travelguideapp.ge.travelguide.helper;

import android.content.Context;
import android.widget.Toast;

import travelguideapp.ge.travelguide.R;

public class MyToaster {

    public static void showUnknownErrorToast(Context context) {
        if (context != null)
            Toast.makeText(context, context.getString(R.string.default_error_message), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        try {
            if (context != null && message != null)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
