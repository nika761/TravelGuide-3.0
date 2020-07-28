package com.example.travelguide.profile.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPermissions;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;

public class ChangePhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button takePhotoBtn, choosePhotoBtn, cancelBtn;
    private ImageView imageView;
    private static final int TAKE_PHOTO_REQUEST = 1234;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_photo);
        initUI();
    }


    private void initUI() {
        takePhotoBtn = findViewById(R.id.take_photo);
        takePhotoBtn.setOnClickListener(this);
        choosePhotoBtn = findViewById(R.id.choose_from_gallery);
        choosePhotoBtn.setOnClickListener(this);
        cancelBtn = findViewById(R.id.change_photo_cancel);
        cancelBtn.setOnClickListener(this);
        imageView.findViewById(R.id.change_photo_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo:

                if (!HelperPermissions.isCameraPermission(this)) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PHOTO_REQUEST);
                } else {
                    HelperPermissions.requestCameraPermission(this);
                }

                break;

            case R.id.choose_from_gallery:
                break;

            case R.id.change_photo_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == HelperPermissions.CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PHOTO_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

}
