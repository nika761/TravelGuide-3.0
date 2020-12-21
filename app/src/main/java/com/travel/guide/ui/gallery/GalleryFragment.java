package com.travel.guide.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.helper.MyToaster;
import com.travel.guide.helper.SystemManager;

import java.util.ArrayList;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class GalleryFragment extends Fragment {

    private Context context;
    private int widthDiff;

    private ArrayList<String> pathsGlobal;

    private boolean is_image;

    private GalleryAdapter galleryAdapter;
    private RecyclerView galleryRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_media, container, false);
        galleryRecycler = layout.findViewById(R.id.media_recyclerview);
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(() -> {
//            layout.getRootView().getDisplay().getMetrics(displayMetrics);
//            widthDiff = displayMetrics.widthPixels;
////                widthDiff = layout.getRootView().getWidth() / 3;
////                try {
////                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//        });

//        widthDiff = layout.getRootView().getWidth() / 3;

        try {
            if (getArguments() != null) {
//                int wd = widthDiff / 3;
                is_image = getArguments().getBoolean("is_image");

                galleryAdapter = new GalleryAdapter(context, is_image);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                galleryRecycler.setLayoutManager(gridLayoutManager);
                galleryRecycler.setHasFixedSize(true);
                int width = HelperMedia.getScreenWidth((Activity) context);
                if (width != 0)
                    galleryAdapter.setItemWidth(width);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPathsAsync(is_image ? 1 : 2);
    }


    private void getPathsAsync(int type) {
        switch (type) {
            case 1:
                new Thread(() -> {
                    try {
                        pathsGlobal = HelperMedia.getImagesPathByDate(context);
                        if (pathsGlobal != null) {
                            galleryAdapter.setItems(pathsGlobal);
                            galleryRecycler.setAdapter(galleryAdapter);
                        }
                    } catch (Exception e) {
//                        runOnUiThread(() -> MyToaster.getErrorToaster(context, "Content Provider Failed Photos"));
                        e.printStackTrace();
                    }
                }).start();
                break;

            case 2:
                new Thread(() -> {
                    try {
                        pathsGlobal = HelperMedia.getVideosPathByDate(context);
                        if (pathsGlobal != null) {
                            galleryAdapter.setItems(pathsGlobal);
                            galleryRecycler.setAdapter(galleryAdapter);
                        }
                    } catch (Exception e) {
//                        runOnUiThread(() -> MyToaster.getErrorToaster(context, "Content Provider Failed Videos"));
                        e.printStackTrace();
                    }
                }).start();
                break;
        }

    }

    private ArrayList<String> fetchMedia(int type) {
        ArrayList<String> listOfAllItems;
        if (type == 1) {
            listOfAllItems = HelperMedia.getImagesPathByDate(context);
            return listOfAllItems;
        }
        listOfAllItems = HelperMedia.getVideosPathByDate(context);
        return listOfAllItems;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public interface ItemCountChangeListener {
        void onItemSelected(String path);

        void onItemRemoved(ArrayList<String> items);
    }
}
