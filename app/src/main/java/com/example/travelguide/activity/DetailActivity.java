package com.example.travelguide.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.utils.Utils;
import com.example.travelguide.R;
import com.example.travelguide.utils.UtilsGlide;

import java.io.File;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {


    private ImageView imageView;
    private VideoView videoView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
//            videoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath() + path);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.setOnPreparedListener(mp -> {
                videoView.setMediaController(new MediaController(DetailActivity.this));
                mp.setLooping(true);
                videoView.start();
            });
        }
    }
}
