package com.example.travelguide.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.UploadStoryAdapter;

import java.util.ArrayList;

public class UploadStoryActivity extends AppCompatActivity {
    private static final int SELECT_IMAGE = 111;
    private static final int EXTERNAL_STORE_CODE = 222;
    private TextView btnNext, btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_story);
        initUI();
        setClickListeners();
        checkExStoragePermission();
    }

    private void initUI() {
        btnNext = findViewById(R.id.btn_next_upload_story);
        btnBack = findViewById(R.id.btn_back_upload_story);
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && data != null) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    pickImages(data);
                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
//            Uri uri = data.getData();
//                    Uri contentUri = MediaStore.Images.Media.getContentUri(data.getDataString());
//                    Toast.makeText(this, data.getDataString(), Toast.LENGTH_SHORT).show();
    }

    private void pickImages(Intent data) {
        if (data.getClipData() != null) {
            ArrayList<Uri> uriArrayList = new ArrayList<>();
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                Uri itemUri = data.getClipData().getItemAt(i).getUri();
                uriArrayList.add(itemUri);
            }
            if (uriArrayList.size() <= 10) {
                iniRecyclerAdapter(uriArrayList);
            } else {
                Toast.makeText(this, "Max photos is 10", Toast.LENGTH_LONG).show();
                openGallery();
            }
        } else {
            Toast.makeText(this, "Data null", Toast.LENGTH_SHORT).show();
        }
    }

    private void iniRecyclerAdapter(ArrayList<Uri> uris) {
        RecyclerView recyclerView = findViewById(R.id.recycler_post);
        UploadStoryAdapter adapter = new UploadStoryAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setUriArrayList(uris);
    }

    private void requestExStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case EXTERNAL_STORE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                break;
        }
    }

    private boolean isExStoragePermissionGranted() {
        boolean permissionGranted = false;
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        }
        return permissionGranted;
    }

    public void checkExStoragePermission() {
        if (isExStoragePermissionGranted()) {
            openGallery();
        } else {
            requestExStoragePermission();
        }
    }
}
