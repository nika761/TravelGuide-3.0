package com.example.travelguide.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.utils.UtilsGlide;

import java.util.Objects;

public class MediaDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);
        Window window = Objects.requireNonNull(this).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        iniUI();
    }

    private void iniUI() {
        imageView = findViewById(R.id.image_detail);
        videoView = findViewById(R.id.video_detail);

        String path = getIntent().getStringExtra("path");

        if (getIntent().getBooleanExtra("is_image", true)) {
            UtilsGlide.loadPhoto(this, path, imageView);
        } else {
            Uri video = Uri.parse(path);
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(path);
//            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.setOnPreparedListener(mp -> {
                videoView.setMediaController(new MediaController(MediaDetailActivity.this));
                mp.setLooping(true);
                videoView.start();
            });
        }
    }
}