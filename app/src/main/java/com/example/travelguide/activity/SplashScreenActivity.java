package com.example.travelguide.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelguide.R;
import com.example.travelguide.utils.UtilsGoogle;
import com.example.travelguide.utils.UtilsPref;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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
        new Thread(() -> Glide.get(this).clearDiskCache()).start();
        Glide.get(this).clearMemory();
    }

    private void setAnimation() {
        Animation sunAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_main_icon_sun);
        Animation goAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_just_go);
        goAnimation.setStartOffset(50);
        sunAnimation.setStartOffset(50);
        mainIconSun.setAnimation(sunAnimation);
        justGoTxt.setAnimation(goAnimation);
    }

    private void checkLastSignedUser() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            String personPhotoUrl;
            Uri personPhotoUri = account.getPhotoUrl();
            if (personPhotoUri != null) {
                personPhotoUrl = personPhotoUri.toString();
            } else {
                personPhotoUrl = null;
            }
            Intent intent = new Intent(SplashScreenActivity.this, UserPageActivity.class);
            intent.putExtra("name", account.getGivenName());
            intent.putExtra("lastName", account.getFamilyName());
            intent.putExtra("email", account.getEmail());
            intent.putExtra("url", personPhotoUrl);
            intent.putExtra("id", account.getId());
            intent.putExtra("loginType", "google");
            startActivity(intent);
        } else {
            Intent mainIntent = new Intent(SplashScreenActivity.this, SignInActivity.class);
            startActivity(mainIntent);
        }

    }


    private void startApplication() {
        new Handler().postDelayed(() -> {

//            if (UtilsPref.getLanguageId(this) != 0) {
//                checkLastSignedUser();
//            } else {
                Intent mainIntent = new Intent(SplashScreenActivity.this, ChooseLanguageActivity.class);
                startActivity(mainIntent);
//            }

            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
