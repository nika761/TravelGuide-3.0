package com.example.travelguide.ui.upload.activity;

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
import com.example.travelguide.ui.upload.adapter.pager.GalleryPagerAdapter;
import com.example.travelguide.ui.upload.adapter.recycler.GalleryAdapterMin;
import com.example.travelguide.ui.upload.fragment.GalleryFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.example.travelguide.ui.upload.activity.EditPostActivity.STORIES_PATHS;

public class GalleryPickerActivity extends AppCompatActivity implements GalleryFragment.ItemCountChangeListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button nextBtn;
    private ImageButton closeBtn;
    private ArrayList<String> pickedItems = new ArrayList<>();
    private GalleryAdapterMin galleryAdapterMin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_picker);
        iniUI();
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        initRecycler();
    }

    private void iniUI() {
        tabLayout = findViewById(R.id.media_tab);
        viewPager = findViewById(R.id.media_view_pager);
        recyclerView = findViewById(R.id.choosed_photos_min_recycler);

        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this);

        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(this);

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
            nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_agree,null));
            if (pickedItems.size() == 0) {
                setViewVisibility(false);
            }
            nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
            galleryAdapterMin.setItemsPath(pickedItems);

        } else {
            if (pickedItems.size() <= 9) {
                pickedItems.add(path);
                nextBtn.setClickable(true);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_agree,null));
            } else {
                nextBtn.setClickable(false);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_next_btn_grey,null));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:

                Intent intent = new Intent(GalleryPickerActivity.this, EditPostActivity.class);
                intent.putStringArrayListExtra(STORIES_PATHS, pickedItems);
                startActivity(intent);

                break;

            case R.id.close_btn:
                finish();
                break;
        }
    }
}
