package travelguideapp.ge.travelguide.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import travelguideapp.ge.travelguide.model.customModel.ItemMedia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import travelguideapp.ge.travelguide.model.parcelable.MediaFileData;

public class HelperMedia {

    public final static int REQUEST_PICK_IMAGE = 28;

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

    public static long getVideoDurationLong(String path) {
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
//                    MediaStore.Images.Media._ID,
//                    MediaStore.Images.Media.DISPLAY_NAME,
//                    MediaStore.Images.Media.DURATION,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.SIZE};

//            String selection = MediaStore.Images.Media.DURATION + " >= ?";
//            String[] selectionArgs = new String[]{String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES))};

//            String sortOrder = MediaStore.Images.Media.DISPLAY_NAME + " DESC";
            String sorting = MediaStore.Images.Media.DATE_ADDED + " DESC";


            try (Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sorting
            )) {
                // Cache column indices.
//                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//                int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
//                int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DURATION);
//                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

                if (cursor != null)
                    while (cursor.moveToNext()) {
                        // Get values of columns for a given video.
//                    long id = cursor.getLong(idColumn);
//                    String name = cursor.getString(nameColumn);
//                    int duration = cursor.getInt(durationColumn);
//                    int size = cursor.getInt(sizeColumn);

                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                        listOfAllPaths.add(path);
                    }
            }
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
        }

        return listOfAllPaths;
    }

    public static ArrayList<String> getVideosPathByDate(Context context) {
        ArrayList<String> listOfAllPaths = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String[] projection = new String[]{
//                    MediaStore.Video.Media._ID,
//                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DATA,
//                    MediaStore.Video.Media.DURATION,
//                    MediaStore.Video.Media.SIZE
            };
//            String selection = MediaStore.Video.Media.DURATION + " >= ?";

//            String[] selectionArgs = new String[]{String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES))};

//            String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " DESC";
            String sorting = MediaStore.Video.Media.DATE_ADDED + " DESC";

            try (Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sorting)) {
                // Cache column indices.
                if (cursor != null) {
//                    int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
//                    int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
//                    int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
//                    int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

                    while (cursor.moveToNext()) {
                        // Get values of columns for a given video.
//                        long id = cursor.getLong(idColumn);
//                        String name = cursor.getString(nameColumn);
//                        int duration = cursor.getInt(durationColumn);
//                        int size = cursor.getInt(sizeColumn);

                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));

//                        String videoDuration = String.valueOf(duration);
//                        Log.e("asdasdasd", videoDuration);

                        // Stores column values and the contentUri in a local object
                        // that represents the media file.
                        listOfAllPaths.add(path);
                    }
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

    public static List<MediaFileData> convertMediaToPng(List<MediaFileData> itemMedia) {

        List<MediaFileData> convertedImages = new ArrayList<>();

        for (MediaFileData item : itemMedia) {
            if (item.getMediaType() == MediaFileData.MediaType.PHOTO) {
                try {
                    Bitmap bmp = BitmapFactory.decodeFile(item.getMediaPath());

                    File imageFileJGP = new File(item.getMediaPath());

                    File imageFilePNG = new File(imageFileJGP.getParent() + "/" + System.currentTimeMillis() + ".png");

                    FileOutputStream out = new FileOutputStream(imageFilePNG);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.flush();
                    out.close();
                    convertedImages.add(new MediaFileData(imageFilePNG.getAbsolutePath(), MediaFileData.MediaType.PHOTO));

                }
                catch (Exception e) {
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

    public static void loadCirclePhotoProfile(Context context, String url, CircleImageView circleImageView) {
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerInside())
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
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else {
                return null;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getPathFromImageUri(Activity activity, Uri uri) {
        Cursor cursor = null;
        String picturePath;
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            cursor = activity.getContentResolver().query(uri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
            } else {
                picturePath = null;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return picturePath;
    }

    public static int getScreenWidth(Activity activity) {
        int itemWidth = 0;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            itemWidth = width / 3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemWidth;
    }

    public static void startImagePicker(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

}
