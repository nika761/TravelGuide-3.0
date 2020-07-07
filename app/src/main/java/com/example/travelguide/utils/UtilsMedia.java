package com.example.travelguide.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class UtilsMedia {

    public static String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
