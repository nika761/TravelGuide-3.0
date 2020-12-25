package travelguideapp.ge.travelguide.ui.gallery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.ui.editPost.EditPostActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.MessageFormat;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements GalleryFragment.ItemCountChangeListener {

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

        if (SystemManager.isReadStoragePermission((this))) {
            getMediaFilesByRole();
        } else {
            SystemManager.requestReadStoragePermission(this);
        }

        initRecycler();

    }

    private void getMediaFilesByRole() {
        if (GlobalPreferences.getUserRole(this) == 0)
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
                Toast.makeText(GalleryActivity.this, "Please choose item", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton closeBtn = findViewById(R.id.close_btn);
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
        bundle.putBoolean("is_image", true);
        HelperUI.loadFragment(galleryFragment, bundle, R.id.gallery_fragment_container, false, true, this);

    }

    private void iniViewPager(ViewPager viewPager) {

        fragmentContainer.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);

        GalleryPagerAdapter galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
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
                nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_agree, null));
                nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
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
                    nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
                    galleryAdapterMin.setItemsPath(pickedItems);
                    isVideo = false;
                } else {
                    Toast.makeText(this, "You can choose only one video", Toast.LENGTH_SHORT).show();
                }

            } else {
                if (pickedItems.size() < 1) {
                    pickedItems.add(path);
                    nextBtn.setClickable(true);
                    nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_agree, null));
                } else {
                    nextBtn.setClickable(false);
                    nextBtn.setBackground(getResources().getDrawable(R.drawable.bg_next_btn_grey, null));
                    Toast.makeText(this, "You can choose only one photo", Toast.LENGTH_SHORT).show();
                }
                choosedItemRecyclerVisibility(true);
                nextBtn.setText(MessageFormat.format("Next ({0})", pickedItems.size()));
                galleryAdapterMin.setItemsPath(pickedItems);
            }
        }
    }

    @Override
    public void onItemRemoved(ArrayList<String> list) {
        if (list.size() == 0) {
            choosedItemRecyclerVisibility(false);
        } else {
            nextBtn.setText(MessageFormat.format("Next ({0})", list.size()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SystemManager.READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMediaFilesByRole();
            } else {
                Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
