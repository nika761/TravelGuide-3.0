package travelguideapp.ge.travelguide.ui.language;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.response.LanguageStringsResponse;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

import java.util.List;
import java.util.Locale;

import travelguideapp.ge.travelguide.ui.splashScreen.SplashScreenActivity;

public class LanguageActivity extends AppCompatActivity implements LanguageListener {
    private LanguagePresenter languagePresenter;
    private FrameLayout loaderContainer;
    private LottieAnimationView loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        loaderContainer = findViewById(R.id.frame_language_loader);
        loader = findViewById(R.id.animation_view_languages);

        languagePresenter = new LanguagePresenter(this);

        try {
            List<LanguagesResponse.Language> languages = (List<LanguagesResponse.Language>) getIntent().getSerializableExtra(SplashScreenActivity.INTENT_LANGUAGES);
            if (languages != null) {
                iniRecyclerAdapter(languages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Button crashButton = new Button(this);
//        crashButton.setText("Crash!");
//        crashButton.setOnClickListener(view -> {
//            throw new RuntimeException("Test Crash"); // Force a crash
//        });
//
//        crashButton.performClick();
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void iniRecyclerAdapter(List<LanguagesResponse.Language> languages) {
        RecyclerView languageRecycler = findViewById(R.id.language_recycler);
        languageRecycler.setLayoutManager(new LinearLayoutManager(this));
        languageRecycler.setHasFixedSize(true);
        languageRecycler.setAdapter(new LanguageAdapter(this, languages));
    }

    @Override
    public void onChooseLanguage(int languageId) {
        try {

            GlobalPreferences.setLanguageId(languageId);

            switch (languageId) {
                case 1:
                    changeLanguage("ka");
                    break;
                case 2:
                    changeLanguage("en");
                    break;
                case 3:
                    changeLanguage("ru");
                    break;
                case 4:
                    changeLanguage("zh");
                    break;
            }

//            loaderContainer.setVisibility(View.VISIBLE);
//            loader.setVisibility(View.VISIBLE);
//            GlobalPreferences.saveLanguageId(this, languageId);
//            languagePresenter.getLanguageStrings(new LanguageStringsRequest(languageId));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeLanguage(String language) {
        GlobalPreferences.setLanguage(language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
//        SystemManager.updateCurrentLanguage(this);
        Intent signIntent = new Intent(LanguageActivity.this, SignInActivity.class);
        startActivity(signIntent);
    }

    @Override
    public void onGetStrings(LanguageStringsResponse languageStringsResponse) {
//        if (languageStringsResponse.getGlobalLanguages() != null)
//            try {
//                new Handler().post(() -> GlobalPreferences.saveCurrentLanguage(LanguageActivity.this, languageStringsResponse.getGlobalLanguages()));
//                new Handler().postDelayed(() -> {
//                    Intent signIntent = new Intent(LanguageActivity.this, SignInActivity.class);
//                    startActivity(signIntent);
//                }, 2000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }

    @Override
    public void onGetError(String error) {
        loaderContainer.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
        MyToaster.showToast(this, error);
    }

    @Override
    protected void onStop() {
        if (languagePresenter != null)
            languagePresenter = null;
        super.onStop();
    }
}
