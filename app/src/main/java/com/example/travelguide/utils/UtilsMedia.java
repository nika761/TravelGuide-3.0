package com.example.travelguide.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;


import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UtilsMedia {
    static int current = 0;


    public static String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static String setVideoDuration(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        retriever.release();

        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );
    }

    public static ArrayList<String> getImagesPathByDate(Context context) {
        ArrayList<String> listOfAllPaths = new ArrayList<>();

        String[] projection = new String[]{
                "COUNT(*) as count", MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA,
                "MAX (" + MediaStore.Images.ImageColumns.DATE_TAKEN + ") as max"};

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                "1) GROUP BY (" + MediaStore.Images.ImageColumns.DATE_TAKEN,
                null,
                "max DESC");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    //gets image path, it will always be a latest image because of sortOrdering by MAX date_taken
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //gets count via alias ("as count" in projection)
                    int count = cursor.getInt(cursor.getColumnIndex("count"));
                    //do you logic here
                    listOfAllPaths.add(path);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return listOfAllPaths;
    }

    public static ArrayList<String> getVideosPathByDate(Context context) {
        ArrayList<String> listOfAllPaths = new ArrayList<>();

        String[] projection = new String[]{
                "COUNT(*) as count", MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME, MediaStore.Video.VideoColumns.DATA,
                "MAX (" + MediaStore.Video.VideoColumns.DATE_TAKEN + ") as max"};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                "1) GROUP BY (" + MediaStore.Video.VideoColumns.DATE_TAKEN,
                null,
                "max DESC");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    //gets image path, it will always be a latest image because of sortOrdering by MAX date_taken
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    //gets count via alias ("as count" in projection)
                    int count = cursor.getInt(cursor.getColumnIndex("count"));
                    //do you logic here
                    listOfAllPaths.add(path);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return listOfAllPaths;
    }

    public static void reduceVideoQuality(ArrayList<String> paths, VideoQualityCallBaack videoQualityCallBaack, Context context) {
        int size = paths.size();
        for (int i = 0; i < size; i++) {
            String path = paths.get(i);
            String destPath = Environment.getExternalStorageDirectory().toString() + File.separator + "temp" + File.separator + "Videos" + File.separator;
            VideoCompressor.INSTANCE.start(path, destPath, new CompressionListener() {
                @Override
                public void onStart() {
                    videoQualityCallBaack.onStart();
                    // Compression start
                }

                @Override
                public void onSuccess() {
                    videoQualityCallBaack.onQualityReduced(destPath);
                }

                @Override
                public void onFailure() {
                    videoQualityCallBaack.onFail();
                    // On Failure
                }

                @Override
                public void onProgress(float v) {
                    videoQualityCallBaack.onProgress();
                    // Update UI with progress value
                }

                @Override
                public void onCancelled() {
                    videoQualityCallBaack.onCancel();
                    // On Cancelled
                }
            }, VideoQuality.LOW, false);
        }
    }

    public interface VideoQualityCallBaack {
        void onQualityReduced(String destPath);

        void onStart();

        void onFail();

        void onProgress();

        void onCancel();
    }
}
