package com.example.travelguide.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;


import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static String encodeVideo(String path) {
        File tempFile = new File(path);
        String encodedString = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            if (inputStream != null) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        encodedString = com.migcomponents.migbase64.Base64.encodeToString(bytes, true);
        Log.e("Strng", encodedString);

        return encodedString;
//        byte[] decodedBytes = com.migcomponents.migbase64.Base64.decodeFast(encodedString.getBytes());
//
//        try {
//
//            FileOutputStream out = new FileOutputStream(
//                    Environment.getExternalStorageDirectory()
//                            + path);
//            out.write(decodedBytes);
//            out.close();
//        } catch (Exception e) {
//            // TODO: handle exception
//            Log.e("Error", e.toString());
//
//        }

    }

    public static String getVideoDuration(String path) {
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
    public static int getVideoDurationInt(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        retriever.release();
        int i = (int) duration;
        return i;
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

    public static void reduceVideoQuality(ArrayList<String> paths, VideoQualityCallBack videoQualityCallBack, Context context) {
        int size = paths.size();
        for (int i = 0; i < size; i++) {
            File file = new File(paths.get(i));
            File downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File desFile = new File(downloadsPath, "es" + file.getName());
            if (desFile.exists()) {
                desFile.delete();
                try {
                    desFile.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
//            String destPath = Environment.getExternalStorageDirectory().toString() + File.separator + "temp" + File.separator + "compressed.mp4";
            VideoCompressor.INSTANCE.start(paths.get(i), desFile.getPath(), new CompressionListener() {
                @Override
                public void onStart() {
                    videoQualityCallBack.onStart();
                    // Compression start
                }

                @Override
                public void onSuccess() {
                    videoQualityCallBack.onQualityReduced(desFile.getPath());
                }

                @Override
                public void onFailure() {
                    videoQualityCallBack.onFail();
                    // On Failure
                }

                @Override
                public void onProgress(float v) {
                    videoQualityCallBack.onProgress();
                    // Update UI with progress value
                }

                @Override
                public void onCancelled() {
                    videoQualityCallBack.onCancel();
                    // On Cancelled
                }
            }, VideoQuality.MEDIUM, true);
        }
    }

    public interface VideoQualityCallBack {
        void onQualityReduced(String destPath);

        void onStart();

        void onFail();

        void onProgress();

        void onCancel();
    }
}
