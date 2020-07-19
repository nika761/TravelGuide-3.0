package com.example.travelguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.adapter.pager.GalleryPagerAdapter;
import com.example.travelguide.adapter.recycler.GalleryAdapterMin;
import com.example.travelguide.fragments.GalleryFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.MessageFormat;
import java.util.ArrayList;

public class GalleryPickerActivity extends AppCompatActivity implements GalleryFragment.ItemCountChangeListener {

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button nextBtn;
    private ImageButton closeBtn;
    private ArrayList<String> selectedImagesPaths = new ArrayList<>();
    private ArrayList<String> selectedVideoPaths = new ArrayList<>();
    private ArrayList<String> pickedItems = new ArrayList<>();
    private GalleryAdapterMin galleryAdapterMin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_picker);
        iniUI();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setClickListeners();
        initRecycler();
    }

    private void setClickListeners() {
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(GalleryPickerActivity.this, UploadStoryActivity.class);
            intent.putStringArrayListExtra("selectedPaths", pickedItems);
            startActivity(intent);
        });

        closeBtn.setOnClickListener(v -> finish());
    }

    private void iniUI() {
        tabLayout = findViewById(R.id.media_tab);
        viewPager = findViewById(R.id.media_view_pager);
        nextBtn = findViewById(R.id.next_btn);
        closeBtn = findViewById(R.id.close_btn);
        recyclerView = findViewById(R.id.choosed_photos_min_recycler);
    }

    private void initRecycler() {
        galleryAdapterMin = new GalleryAdapterMin(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(galleryAdapterMin);
    }

    private void setViewPager(ViewPager viewPager) {
        GalleryPagerAdapter galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(galleryPagerAdapter);
    }

    private void setViewVisibility(boolean isShow) {
        if (isShow) {
            nextBtn.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            nextBtn.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(String path) {
        if (pickedItems.size() != 0 && pickedItems.contains(path)) {
            setViewVisibility(true);
            pickedItems.remove(path);
            nextBtn.setClickable(true);
            nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_agree));
            if (pickedItems.size() == 0) {
                setViewVisibility(false);
            }
            nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
            galleryAdapterMin.setItemsPath(pickedItems);

        } else {
            if (pickedItems.size() <= 9) {
                pickedItems.add(path);
                nextBtn.setClickable(true);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_agree));
            } else {
                nextBtn.setClickable(false);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_next_btn_grey));
            }
            setViewVisibility(true);
            nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
            galleryAdapterMin.setItemsPath(pickedItems);
        }
    }

    @Override
    public void onItemRemoved(ArrayList<String> list) {
        if (list.size() == 0) {
            setViewVisibility(false);
        } else {
            nextBtn.setText(MessageFormat.format("Next ({0})", list.size()));
        }
    }
}
