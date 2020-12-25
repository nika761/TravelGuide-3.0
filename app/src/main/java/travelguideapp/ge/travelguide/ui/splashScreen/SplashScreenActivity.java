package travelguideapp.ge.travelguide.ui.splashScreen;

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

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.ui.language.LanguageActivity;
import travelguideapp.ge.travelguide.ui.language.LanguageListener;
import travelguideapp.ge.travelguide.ui.login.loggedUsers.SavedUserActivity;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;
import travelguideapp.ge.travelguide.ui.language.LanguagePresenter;
import travelguideapp.ge.travelguide.utility.BaseApplication;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity implements LanguageListener.SplashListener {

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
        calculateScreenWidth();
        initUI();
        setAnimation();
        checkNetwork();
    }

    private void calculateScreenWidth() {
        try {
            BaseApplication.ITEM_WIDTH_FOR_POSTS = HelperMedia.getScreenWidth(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkNetwork() {
        if (SystemManager.checkNetworkConnection(this)) {
            if (GlobalPreferences.getLanguageId(this) != 0) {
                if (GlobalPreferences.getAccessToken(this) != null)
                    openHome();
                else
                    openSign();
            } else {
                languagePresenter.sentLanguageRequest();
            }
        } else {
            MyToaster.getErrorToaster(this, getString(R.string.no_internet_connection));
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
    protected void onDestroy() {
        if (languagePresenter != null)
            languagePresenter = null;
        super.onDestroy();
    }
}
