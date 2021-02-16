package travelguideapp.ge.travelguide.ui.editPost.filterActivity;

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

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.SystemManager;

import java.io.File;
import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class FilterActivity extends AppCompatActivity implements IFilterListener, View.OnClickListener {

    private PhotoEditorView photoEditorView;
    private PhotoEditor photoEditor;

    private String path;
    private String originalFileParentPath;
    private int position;

    private TextView undo;

    private ArrayList<String> imagePathes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        SystemManager.setLanguage(this);
        if (!SystemManager.isWriteStoragePermission(this)) {
            SystemManager.requestWriteStoragePermission(this);
        }

        try {
            this.position = getIntent().getIntExtra("position_for_image", 0);
            this.path = getIntent().getStringExtra("image_for_filter");
            setOriginalFilePath(path);
            initPhotoEditor(path);
            initFiltersAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }


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

        undo = findViewById(R.id.filter_undo);
        undo.setOnClickListener(this);

        photoEditorView = findViewById(R.id.filter_photo);
        photoEditor = new PhotoEditor.Builder(this, photoEditorView).setPinchTextScalable(true).build();
        photoEditorView.getSource().setImageURI(Uri.fromFile(new File(path)));

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        try {
            if (photoFilter == PhotoFilter.NONE) {
                photoEditorView.getSource().setImageURI(Uri.fromFile(new File(path)));
            } else {
                try {
                    photoEditor.setFilterEffect(photoFilter);
                    saveFilterImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void saveFilterImage() {
        try {

            File file = new File(getOriginalFileParentPath() + "/" + System.currentTimeMillis() + ".jgp");
            String pathForFiltered = file.getAbsolutePath();

            photoEditor.saveAsFile(pathForFiltered, new PhotoEditor.OnSaveListener() {
                @Override
                public void onSuccess(@NonNull String imagePath) {
                    imagePathes.add(imagePath);
                }

                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(FilterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_done:
                try {
                    Intent resultIntent = new Intent();
                    if (imagePathes.size() > 0) {
                        resultIntent.putExtra("filtered_path", imagePathes.get(imagePathes.size() - 1));
                    } else {
                        resultIntent.putExtra("filtered_path", path);
                    }
                    resultIntent.putExtra("filtered_position", position);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.filter_close:
                finish();
                break;

            case R.id.filter_undo:
                try {
                    if (imagePathes.size() > 0) {
                        if (imagePathes.size() == 1) {
                            photoEditorView.getSource().setImageURI(Uri.fromFile(new File(path)));
                            imagePathes.remove(0);
                        } else {
                            photoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePathes.get(imagePathes.size() - 2))));
                            imagePathes.remove(imagePathes.size() - 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setOriginalFilePath(String path) {
        File originalFile = new File(path);
        originalFileParentPath = originalFile.getParent();
    }

    private String getOriginalFileParentPath() {
        return originalFileParentPath;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SystemManager.WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
