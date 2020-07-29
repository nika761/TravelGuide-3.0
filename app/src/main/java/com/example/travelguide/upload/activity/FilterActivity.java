package com.example.travelguide.upload.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.upload.adapter.recycler.FilterAdapter;
import com.example.travelguide.upload.interfaces.IFilterListener;
import com.example.travelguide.helper.HelperPermissions;

import java.io.File;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class FilterActivity extends AppCompatActivity implements IFilterListener, View.OnClickListener {

    private PhotoEditorView photoEditorView;
    private TextView done, undo;
    private ImageButton close;
    private PhotoEditor photoEditor;
    private int position;
    private String path;
    private String newPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        this.position = getIntent().getIntExtra("position_for_image", 0);
        this.path = getIntent().getStringExtra("image_for_filter");
        initPhotoEditor(path);
        initFiltersAdapter();
    }

    private void initFiltersAdapter() {
        RecyclerView recyclerView = findViewById(R.id.recycler_filter);
        FilterAdapter filterAdapter = new FilterAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(filterAdapter);
    }

    private void initPhotoEditor(String path) {
        done = findViewById(R.id.filter_done);
        done.setOnClickListener(this);
        close = findViewById(R.id.filter_close);
        close.setOnClickListener(this);
        undo = findViewById(R.id.filter_undo);
        undo.setOnClickListener(this);
        photoEditorView = findViewById(R.id.filter_photo);
        photoEditor = new PhotoEditor.Builder(this, photoEditorView).setPinchTextScalable(true).build();
        photoEditorView.getSource().setImageURI(Uri.fromFile(new File(path)));
    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        photoEditor.setFilterEffect(photoFilter);
        try {
            saveFilterImage();
        } catch (Exception e) {
            Toast.makeText(this, "error while saving", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveFilterImage() {
        if (HelperPermissions.isWriteStoragePermission(this)) {
            File newFile = new File(path);
            String parent = newFile.getParent();

            File file = new File(parent + "/" + System.currentTimeMillis() + ".jgp");
            String pathForFiltered = file.getAbsolutePath();

            photoEditor.saveAsFile(pathForFiltered, new PhotoEditor.OnSaveListener() {
                @Override
                public void onSuccess(@NonNull String imagePath) {
                    newPath = imagePath;
                }

                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("taggggg", exception.toString());
                    Toast.makeText(FilterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            HelperPermissions.requestWriteStoragePermission(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_done:

                Intent resultIntent = new Intent();
                resultIntent.putExtra("filtered_path", newPath);
                resultIntent.putExtra("filtered_position", position);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                break;

            case R.id.filter_close:
                finish();
                break;

            case R.id.filter_undo:
                photoEditor.clearAllViews();
                break;
        }
    }
}
