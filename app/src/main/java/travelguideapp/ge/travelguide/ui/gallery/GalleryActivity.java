package travelguideapp.ge.travelguide.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.ui.editPost.EditPostActivity;

import com.google.android.material.tabs.TabLayout;

import java.text.MessageFormat;
import java.util.ArrayList;

public class GalleryActivity extends BaseActivity implements GalleryFragment.ItemCountChangeListener {

    private ConstraintLayout fragmentContainer;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button nextBtn;

    private GalleryAdapterMin galleryAdapterMin;
    private ArrayList<String> pickedItems = new ArrayList<>();

    private final int MAX_SIZE_WITH_VIDEO = 8;
    private final int MAX_SIZE_DEFAULT = 9;
    boolean isVideo = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_picker);

        iniUI();

        if (isPermissionGranted(READ_EXTERNAL_STORAGE)) {
            getMediaFilesByRole();
        } else {
            requestPermission(READ_EXTERNAL_STORAGE);
        }

        initRecycler();

    }


    private void getMediaFilesByRole() {
        if (GlobalPreferences.getUserRole() == 0)
            loadGalleryFragment();
        else
            iniViewPager(viewPager);
    }

    private void iniUI() {
        tabLayout = findViewById(R.id.media_tab);
        viewPager = findViewById(R.id.media_view_pager);
        recyclerView = findViewById(R.id.choosed_photos_min_recycler);

        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(v -> {
            if (pickedItems.size() >= 1) {
                Intent intent = new Intent(GalleryActivity.this, EditPostActivity.class);
                intent.putStringArrayListExtra(EditPostActivity.STORIES_PATHS, pickedItems);
                startActivity(intent);
            } else {
                MyToaster.showToast(this, "Please choose item");
            }
        });

        ImageButton closeBtn = findViewById(R.id.close_btn_toolbar);
        closeBtn.setOnClickListener(v -> finish());

        fragmentContainer = findViewById(R.id.gallery_fragment_container);
    }

    private void initRecycler() {
        galleryAdapterMin = new GalleryAdapterMin(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(galleryAdapterMin);
    }

    private void loadGalleryFragment() {

        fragmentContainer.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

        GalleryFragment galleryFragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_image", false);
        HelperUI.loadFragment(galleryFragment, bundle, R.id.gallery_fragment_container, false, true, this);

    }

    private void iniViewPager(ViewPager viewPager) {

        fragmentContainer.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);

        GalleryPagerAdapter galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
        galleryPagerAdapter.setTabs(getString(R.string.video), getString(R.string.photos));
        viewPager.setAdapter(galleryPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void choosedItemRecyclerVisibility(boolean isShow) {
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
            pickedItems.remove(path);
            if (path.endsWith("mp4")) {
                isVideo = true;
            }
            if (pickedItems.size() <= 2) {
                nextBtn.setClickable(true);
                nextBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_agree));
                nextBtn.setText(MessageFormat.format("{0} ({1})", getString(R.string.next), pickedItems.size()));
//                nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
                galleryAdapterMin.setItemsPath(pickedItems);
            }

            if (pickedItems.size() == 0) {
                choosedItemRecyclerVisibility(false);
            }

        } else {
            if (path.endsWith("mp4")) {
                if (isVideo) {
                    pickedItems.add(path);
                    choosedItemRecyclerVisibility(true);
//                    nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
                    nextBtn.setText(MessageFormat.format("{0} ({1})", getString(R.string.next), pickedItems.size()));
                    galleryAdapterMin.setItemsPath(pickedItems);
                    isVideo = false;
                } else {
                    MyToaster.showToast(this, "You can choose only one video");
                }

            } else {
                if (pickedItems.size() < 1) {
                    pickedItems.add(path);
                    nextBtn.setClickable(true);
                    nextBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_agree));
                } else {
                    nextBtn.setClickable(false);
                    nextBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_next_btn_grey));
                    MyToaster.showToast(this, "You can choose only one photo");
                }
                choosedItemRecyclerVisibility(true);
//                nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
                nextBtn.setText(MessageFormat.format("{0} ({1})", getString(R.string.next), pickedItems.size()));
                galleryAdapterMin.setItemsPath(pickedItems);
            }
        }
    }

    @Override
    public void onItemRemoved(ArrayList<String> list) {
        if (list.size() == 0) {
            choosedItemRecyclerVisibility(false);
        } else {
            nextBtn.setText(MessageFormat.format("{0} ({1})", getString(R.string.next), list.size()));
        }
    }

    @Override
    public void onPermissionResult(boolean permissionGranted) {
        if (permissionGranted) {
            getMediaFilesByRole();
        } else {
            MyToaster.showToast(this, "No permission granted");
        }
    }
}
