package com.example.travelguide.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.MediaAdapter;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MediaFragment extends Fragment {

    private Context context;
    private MediaAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.media_fragment, container, false);

        RecyclerView recyclerView = layout.findViewById(R.id.media_recyclerview);
        adapter = new MediaAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        ArrayList<String> stringList = fetchMedia(getArguments().getBoolean("is_image") ? 1 : 2);
//        ArrayList<Uri> uriArrayList = new ArrayList<>();
//        for (int i = 0; i < 11; i++) {
//            File imgFile = new File(stringList.get(i));
//            if (imgFile.exists()) {
//                Uri uri = Uri.fromFile(imgFile);
//                uriArrayList.add(uri);
////                try {
////                    Bitmap original = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
////                    ExifInterface ei = new ExifInterface(uri.getPath());
////                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
////                            ExifInterface.ORIENTATION_UNDEFINED);
////                    Bitmap rotatedBitmap = null;
////                    switch(orientation) {
////
////                        case ExifInterface.ORIENTATION_ROTATE_90:
////                            rotatedBitmap = rotateImage(original, 90);
////                            break;
////
////                        case ExifInterface.ORIENTATION_ROTATE_180:
////                            rotatedBitmap = rotateImage(original, 180);
////                            break;
////
////                        case ExifInterface.ORIENTATION_ROTATE_270:
////                            rotatedBitmap = rotateImage(original, 270);
////                            break;
////
////                        case ExifInterface.ORIENTATION_NORMAL:
////                        default:
////                            rotatedBitmap = original;
////                    }
////
////
////                     bitmaps.add(rotatedBitmap);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//            }
//        }

        adapter.setItems(stringList);


        return layout;
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private ArrayList<String> fetchMedia(int type) {
        ArrayList<String> listOfAllImages = new ArrayList<>();

        if (type == 1) {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String absolutePathOfImage = null;


            String[] projection = {MediaStore.MediaColumns.DATA};
            String[] columns = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.DATE_TAKEN};

            String orderBy = MediaStore.Images.ImageColumns.DATE_ADDED;
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }


        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String absolutePathOfImage = null;


        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            listOfAllImages.add(absolutePathOfImage);
        }

        return listOfAllImages;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
