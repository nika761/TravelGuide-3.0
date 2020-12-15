package com.travel.guide.helper;

import android.content.Context;
import android.widget.Toast;

public class MyToaster {

    public static void getUnknownErrorToast(Context context) {
        Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
    }

    public static void getErrorToaster(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
