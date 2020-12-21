package com.travel.guide.ui.language;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.language.GlobalLanguages;
import com.travel.guide.model.request.LanguageStringsRequest;
import com.travel.guide.model.response.LanguageStringsResponse;
import com.travel.guide.ui.login.signIn.SignInActivity;
import com.travel.guide.model.response.LanguagesResponse;
import com.travel.guide.utility.GlobalPreferences;

import java.util.List;

import static com.travel.guide.ui.splashScreen.SplashScreenActivity.INTENT_LANGUAGES;


public class LanguageActivity extends AppCompatActivity implements LanguageListener {
    private LanguagePresenter languagePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        languagePresenter = new LanguagePresenter(this);

        List<LanguagesResponse.Language> languages = (List<LanguagesResponse.Language>) getIntent().getSerializableExtra(INTENT_LANGUAGES);
        if (languages != null) {
            iniRecyclerAdapter(languages);
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
        GlobalPreferences.saveLanguageId(this, languageId);
        Intent signIntent = new Intent(this, SignInActivity.class);
        startActivity(signIntent);
//        languagePresenter.getLanguageStrings(new LanguageStringsRequest(languageId));
    }

    @Override
    public void onGetStrings(LanguageStringsResponse languageStringsResponse) {
        if (languageStringsResponse.getGlobalLanguages() != null)
            try {
//                saveLanguage(languageStringsResponse.getGlobalLanguages());
            } catch (Exception e) {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
    }

    private void saveLanguage(GlobalLanguages globalLanguages) {
        new Handler().post(() -> GlobalPreferences.saveCurrentLanguage(LanguageActivity.this, globalLanguages));
    }

    @Override
    public void onGetError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        if (languagePresenter != null)
            languagePresenter = null;
        super.onStop();
    }
}
