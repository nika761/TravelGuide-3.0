package com.travel.guide.ui.editPost.filterActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperSystem;

import java.io.File;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

import static com.travel.guide.helper.HelperSystem.WRITE_EXTERNAL_STORAGE;

public class FilterActivity extends AppCompatActivity implements IFilterListener, View.OnClickListener {

    private PhotoEditorView photoEditorView;
    private PhotoEditor photoEditor;

    private String newPath;
    private String path;
    private int position;

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

        TextView done = findViewById(R.id.filter_done);
        done.setOnClickListener(this);

        ImageButton close = findViewById(R.id.filter_close);
        close.setOnClickListener(this);

        TextView undo = findViewById(R.id.filter_undo);
        undo.setOnClickListener(this);

        photoEditorView = findViewById(R.id.filter_photo);
        photoEditor = new PhotoEditor.Builder(this, photoEditorView).setPinchTextScalable(true).build();
        photoEditorView.getSource().setImageURI(Uri.fromFile(new File(path)));

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        photoEditor.setFilterEffect(photoFilter);
        try {
            if (HelperSystem.isWriteStoragePermission(this))
                saveFilterImage();
            else
                HelperSystem.requestWriteStoragePermission(this);

        } catch (Exception e) {
            Toast.makeText(this, "error while saving", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveFilterImage() {
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
                Toast.makeText(FilterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveFilterImage();
                } else {
                    Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
