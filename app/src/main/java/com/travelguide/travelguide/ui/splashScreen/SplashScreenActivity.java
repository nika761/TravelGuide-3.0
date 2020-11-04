package com.travelguide.travelguide.ui.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.travelguide.travelguide.R;
import com.travelguide.travelguide.helper.HelperSystem;
import com.travelguide.travelguide.ui.login.signIn.SignInActivity;
import com.travelguide.travelguide.ui.home.HomePageActivity;
import com.travelguide.travelguide.ui.language.LanguageActivity;
import com.travelguide.travelguide.ui.language.LanguageListener;
import com.travelguide.travelguide.ui.login.loggedUsers.SavedUserActivity;
import com.travelguide.travelguide.model.response.LanguagesResponse;
import com.travelguide.travelguide.ui.language.LanguagePresenter;
import com.travelguide.travelguide.helper.HelperPref;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity implements LanguageListener {

    private final int SPLASH_DISPLAY_LENGTH = 1300;
    private ImageView mainIconSun;
    private TextView mainLogoTxt, justGoTxt;
    private LanguagePresenter languagePresenter;
    private ArrayList<LanguagesResponse.Language> languages;
    public static final String INTENT_LANGUAGES = "languages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initUI();
        setAnimation();
        checkNetwork();
    }

    private void checkNetwork() {
        if (HelperSystem.checkNetworkConnection(this)) {
            if (HelperPref.getLanguageId(this) != 0) {
                if (HelperPref.getAccessToken(this) != null)
                    openHome();
                else
                    openSign();
            } else {
                languagePresenter.sentLanguageRequest();
            }
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    public void initUI() {
        mainIconSun = findViewById(R.id.main_icon_sun);
        mainLogoTxt = findViewById(R.id.main_logo_text);
        justGoTxt = findViewById(R.id.just_go);
        languagePresenter = new LanguagePresenter(this);
        new Thread(() -> Glide.get(this).clearDiskCache()).start();
        Glide.get(this).clearMemory();
    }

    private void setAnimation() {
        Animation sunAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_sun);
        Animation goAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_just_go);
        goAnimation.setStartOffset(50);
        sunAnimation.setStartOffset(50);
        mainIconSun.setAnimation(sunAnimation);
        justGoTxt.setAnimation(goAnimation);
    }

    private void stopAnimation(Animation animation, Animation animationSecond) {
        animation.cancel();
        animationSecond.cancel();
    }

    private void startApplication(List<LanguagesResponse.Language> languages) {
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashScreenActivity.this, LanguageActivity.class);
            mainIntent.setFlags(mainIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            mainIntent.putExtra(INTENT_LANGUAGES, (Serializable) languages);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void openSign() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void openSaved() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, SavedUserActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void openHome() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }


    @Override
    public void onGetLanguages(LanguagesResponse languagesResponse) {
        if (languagesResponse.getStatus() == 0) {
            List<LanguagesResponse.Language> languages = languagesResponse.getLanguage();
            startApplication(languages);
        }
    }

    @Override
    public void onChooseLanguage() {

    }
}
