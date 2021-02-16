package travelguideapp.ge.travelguide.helper;

import android.content.Context;
import android.widget.Toast;

public class MyToaster {

    public static void getUnknownErrorToast(Context context) {
        if (context != null)
            Toast.makeText(context, "Error Try Again", Toast.LENGTH_SHORT).show();
    }

    public static void getToast(Context context, String message) {
        if (context != null && message != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
