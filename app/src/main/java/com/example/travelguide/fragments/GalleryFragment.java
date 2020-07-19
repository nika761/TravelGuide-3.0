package com.example.travelguide.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.GalleryAdapter;
import com.example.travelguide.utils.UtilsMedia;
import com.example.travelguide.utils.UtilsPermissions;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private Context context;
    private GalleryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_media, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.media_recyclerview);
        if (getArguments() != null) {
            adapter = new GalleryAdapter(context, getArguments().getBoolean("is_image"));
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
            if (UtilsPermissions.isReadStoragePermission(getActivity())) {
                adapter.setItems(fetchMedia(getArguments().getBoolean("is_image") ? 1 : 2));
            }
        }
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private ArrayList<String> fetchMedia(int type) {
        ArrayList<String> listOfAllItems;
        if (type == 1) {
            listOfAllItems = UtilsMedia.getImagesPathByDate(context);
            return listOfAllItems;
        }
        listOfAllItems = UtilsMedia.getVideosPathByDate(context);
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
