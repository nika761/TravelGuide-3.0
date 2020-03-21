package com.example.travelguide.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.Utils;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private ImageView mainIconSun;
    private TextView mainLogoTxt, justGoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iniUI();
        setAnimation();
        startApplication();
    }


    public void iniUI() {
        mainIconSun = findViewById(R.id.main_icon_sun);
        mainLogoTxt = findViewById(R.id.main_logo_text);
        justGoTxt = findViewById(R.id.just_go);
    }

    private void setAnimation() {
        Animation sunAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_main_icon_sun);
        mainIconSun.setAnimation(sunAnimation);
        Animation goAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_just_go);
        justGoTxt.setAnimation(goAnimation);
    }

    private void startApplication() {
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashScreenActivity.this, ChooseLanguageActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
