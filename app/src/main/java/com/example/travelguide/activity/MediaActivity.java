package com.example.travelguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.adapter.pager.MediaPagerAdapter;
import com.example.travelguide.adapter.recycler.GalleryAdapterMin;
import com.example.travelguide.adapter.recycler.UploadStoryAdapter;
import com.example.travelguide.fragments.MediaFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MediaActivity extends AppCompatActivity implements MediaFragment.ItemCountChangeListener {

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button nextBtn;
    private ArrayList<String> selectedImagesPaths = new ArrayList<>();
    private ArrayList<String> selectedVideoPaths = new ArrayList<>();
    private ArrayList<String> selectedItemPaths = new ArrayList<>();
    private GalleryAdapterMin galleryAdapterMin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_gallery);
        iniUI();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setClickListeners();
    }

    private void setClickListeners() {
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaActivity.this, UploadStoryActivity.class);
            selectedImagesPaths.addAll(selectedVideoPaths);
            intent.putStringArrayListExtra("selectedPaths", selectedImagesPaths);
            startActivity(intent);
        });
    }

    private void iniUI() {
        tabLayout = findViewById(R.id.media_tab);
        viewPager = findViewById(R.id.media_view_pager);
        nextBtn = findViewById(R.id.next_btn);
        recyclerView = findViewById(R.id.choosed_photos_min_recycler);
    }

    private void initRecycler(boolean isImage) {
        galleryAdapterMin = new GalleryAdapterMin(isImage);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(galleryAdapterMin);
    }


    private void setViewPager(ViewPager viewPager) {
        MediaPagerAdapter mediaPagerAdapter = new MediaPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mediaPagerAdapter);
    }

    private void setBtn(boolean isShow) {
        if (isShow)
            nextBtn.setVisibility(View.VISIBLE);
        else
            nextBtn.setVisibility(View.GONE);
    }


    @Override
    public void imageSelectedPaths(ArrayList<String> paths) {
        selectedImagesPaths = paths;
        if (selectedImagesPaths.size() == 0 && selectedVideoPaths.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            setBtn(false);
        } else {
            setBtn(true);
            initRecycler(true);
            galleryAdapterMin.setItemsPath(selectedImagesPaths);
        }
    }

    @Override
    public void videoSelectedPaths(ArrayList<String> paths) {
        selectedVideoPaths = paths;
        if (selectedImagesPaths.size() == 0 && selectedVideoPaths.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            setBtn(false);
        } else {
            setBtn(true);
            initRecycler(false);
            galleryAdapterMin.setItemsPath(selectedVideoPaths);
        }
    }

}
