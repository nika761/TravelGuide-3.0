package com.example.travelguide.helper;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;


import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.model.ItemMedia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelperMedia {

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

    public static long getVideoDurationInt(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        retriever.release();
        int i = (int) duration;
        return duration;
    }

    public static ArrayList<String> getImagesPathByDate(Context context) {

        ArrayList<String> listOfAllPaths = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            String[] projection = new String[]{
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DURATION,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.SIZE
            };
            String selection = MediaStore.Images.Media.DURATION +
                    " >= ?";
            String[] selectionArgs = new String[]{
                    String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES))};

            String sortOrder = MediaStore.Images.Media.DISPLAY_NAME + " DESC";

            try (Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder
            )) {
                // Cache column indices.
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                int nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                int durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DURATION);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    long id = cursor.getLong(idColumn);
                    String name = cursor.getString(nameColumn);
                    int duration = cursor.getInt(durationColumn);
                    int size = cursor.getInt(sizeColumn);

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));


                    listOfAllPaths.add(path);
                }
            }

            // Do something for lollipop and above versions
        } else {

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
            // do something for phones running an SDK before lollipop
        }

        return listOfAllPaths;
    }

    public static ArrayList<String> getVideosPathByDate(Context context) {
        ArrayList<String> listOfAllPaths = new ArrayList<>();


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String[] projection = new String[]{
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.SIZE
            };
            String selection = MediaStore.Video.Media.DURATION +
                    " >= ?";
            String[] selectionArgs = new String[]{
                    String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES))};

            String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " DESC";

            try (Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder
            )) {
                // Cache column indices.
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                int nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                int durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    long id = cursor.getLong(idColumn);
                    String name = cursor.getString(nameColumn);
                    int duration = cursor.getInt(durationColumn);
                    int size = cursor.getInt(sizeColumn);

                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));

                    // Stores column values and the contentUri in a local object
                    // that represents the media file.
                    listOfAllPaths.add(path);
                }
            }
        } else {
            String[] projection = new String[]{"COUNT(*) as count", MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Video.VideoColumns.DATA,
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
        }

        return listOfAllPaths;
    }

    public static List<ItemMedia> convertImagesToPng(List<ItemMedia> itemMedia) {

        List<ItemMedia> convertedImages = new ArrayList<>();

        for (ItemMedia item : itemMedia) {
            if (item.getType() == 0) {
                try {
                    Bitmap bmp = BitmapFactory.decodeFile(item.getPath());

                    File imageFileJGP = new File(item.getPath());

                    File imageFilePNG = new File(imageFileJGP.getParent() + "/" + System.currentTimeMillis() + ".png");

                    FileOutputStream out = new FileOutputStream(imageFilePNG);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.flush();
                    out.close();
                    convertedImages.add(new ItemMedia(0, imageFilePNG.getAbsolutePath()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return convertedImages;
    }

    public static void loadCirclePhoto(Context context, String url, CircleImageView circleImageView) {
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(url)
                .into(circleImageView);
    }

    public static void loadPhoto(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(url)
                .into(imageView);
    }

    public static String getFilePathFromVideoURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Video.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
