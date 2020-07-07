package com.example.travelguide.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.FilterAdapter;
import com.example.travelguide.adapter.recycler.UploadStoryAdapter;
import com.example.travelguide.interfaces.IFilterListener;
import com.example.travelguide.interfaces.IUploadStory;
import com.example.travelguide.model.request.UploadStoryRequestModel;
import com.example.travelguide.model.response.UploadStoryResponseModel;
import com.example.travelguide.presenters.UploadStoryPresenter;
import com.example.travelguide.utils.UtilsMedia;
import com.example.travelguide.utils.UtilsPermissions;
import com.example.travelguide.utils.UtilsPref;
import com.opensooq.supernova.gligar.GligarPicker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class UploadStoryActivity extends AppCompatActivity implements IUploadStory, IFilterListener {
    private static final int SELECT_IMAGE = 111;
    private static final int PICKER_REQUEST_CODE = 333;
    private TextView btnNext;
    private ImageView btnBack;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private UploadStoryAdapter adapter;
    private int adapterPosition;
    private PhotoEditor photoEditor;
    private PhotoEditorView photoEditorView;
    private List<String> photos = new ArrayList<>();
    private UploadStoryPresenter uploadStoryPresenter;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_story);
        initUI();
        setClickListeners();
        checkPermission();
    }

    private void initUI() {
        uploadStoryPresenter = new UploadStoryPresenter(this);
        photoEditorView = findViewById(R.id.photo_editor_view);
        photoEditor = new PhotoEditor.Builder(this, photoEditorView).setPinchTextScalable(true).build();
        btnNext = findViewById(R.id.btn_next_upload_story);
        btnBack = findViewById(R.id.btn_back_upload_story);
        lottieAnimationView = findViewById(R.id.animation_view_upload);
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());
        btnNext.setOnClickListener(v -> {
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    startUpload();});
    }

    private void startUpload() {
        for (int i = 0; i < uriArrayList.size(); i++) {
            final Uri itemUri = uriArrayList.get(i);
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(itemUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                String encodedImage = UtilsMedia.encodeImage(selectedImage);
                photos.add(encodedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (photos != null) {
            UploadStoryRequestModel uploadStoryRequestModel = new UploadStoryRequestModel(17, photos, null);
            uploadStoryPresenter.uploadStory("Bearer" + " " + UtilsPref.getCurrentAccessToken(this), uploadStoryRequestModel);
        }
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
        switch (requestCode) {
            case SELECT_IMAGE:
                if (data != null) {
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            pickImages(data);
                            break;

                        case Activity.RESULT_CANCELED:
                            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                }


            case PICKER_REQUEST_CODE: {
                if (data != null && data.getExtras() != null) {
                    pickImages(data);
//                    String[] pathsList = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
//                    if (pathsList != null) {
//                        Toast.makeText(this, "Number of " + pathsList.length, Toast.LENGTH_SHORT).show();
//                    }
                }
            }
            break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (result != null) {
                    switch (resultCode) {
                        case RESULT_OK:
                            adapter.onCropResult(result.getUri(), adapterPosition);
                            Toast.makeText(this, "Image Crop Successful", Toast.LENGTH_LONG).show();
                            break;
                        case RESULT_CANCELED:
                            Toast.makeText(this, "Image Crop Error", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
                }
        }
    }

    private void pickImages(Intent data) {
        if (data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                Uri itemUri = data.getClipData().getItemAt(i).getUri();
                uriArrayList.add(itemUri);
            }
            if (uriArrayList.size() <= 10) {
                initContentRecyclerAdapter(uriArrayList);
                initFiltersAdapter();
            } else {
                Toast.makeText(this, "Max photos is 10", Toast.LENGTH_LONG).show();
                openGallery();
            }
        } else if (data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                photoEditorView.getSource().setImageBitmap(bitmap);
//                photoEditorView.getSource().setImageBitmap(bitmap);
                initFiltersAdapter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (data.getExtras() != null) {
            String[] pathsList = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
            if (pathsList != null) {
                List<String> stringList = new ArrayList<>(Arrays.asList(pathsList));
                for (String s : stringList) {
                    File imgFile = new File(s);
                    if (imgFile.exists()) {
                        Uri uri = Uri.fromFile(imgFile);
                        uriArrayList.add(uri);
                    }
                    if (uriArrayList.size() <= 10) {
                        initContentRecyclerAdapter(uriArrayList);
                        initFiltersAdapter();
                    }
                }
            }
        }
    }

    private void initContentRecyclerAdapter(ArrayList<Uri> uris) {
        RecyclerView recyclerView = findViewById(R.id.recycler_post);
        adapter = new UploadStoryAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setUriArrayList(uris);
    }

    private void initFiltersAdapter() {
        RecyclerView recyclerView = findViewById(R.id.story_tools_container);
        FilterAdapter filterAdapter = new FilterAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(filterAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case UtilsPermissions.READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new GligarPicker().requestCode(PICKER_REQUEST_CODE).withActivity(this).show();
                }
                break;
        }
    }

    public void checkPermission() {
        if (UtilsPermissions.isExStoragePermissionGranted(this)) {
            new GligarPicker().requestCode(PICKER_REQUEST_CODE).withActivity(this).show();
//            openGallery();
        } else {
            UtilsPermissions.requestExStoragePermission(this);
        }
    }

    @Override
    public void onGetItem(Uri uri, int position) {
        this.adapterPosition = position;
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true).start(this);
    }

    @Override
    public void onStoryUploaded(UploadStoryResponseModel uploadStoryResponseModel) {
        lottieAnimationView.setVisibility(View.GONE);
        if (uploadStoryResponseModel.getStatus() == 0) {
            Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show();
        } else if (uploadStoryResponseModel.getStatus() == 1) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Some error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        photoEditor.setFilterEffect(photoFilter);
    }


}
