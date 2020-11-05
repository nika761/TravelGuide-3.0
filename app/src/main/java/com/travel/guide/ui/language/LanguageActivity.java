package com.travel.guide.ui.language;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.ui.login.signIn.SignInActivity;
import com.travel.guide.model.response.LanguagesResponse;

import java.util.List;

import static com.travel.guide.ui.splashScreen.SplashScreenActivity.INTENT_LANGUAGES;


public class LanguageActivity extends AppCompatActivity implements LanguageListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        List<LanguagesResponse.Language> languages = (List<LanguagesResponse.Language>) getIntent().getSerializableExtra(INTENT_LANGUAGES);
        if (languages != null) {
            iniRecyclerAdapter(languages);
        }

    }

    private void iniRecyclerAdapter(List<LanguagesResponse.Language> languages) {
        RecyclerView languageRecycler = findViewById(R.id.language_recycler);
        languageRecycler.setLayoutManager(new LinearLayoutManager(this));
        languageRecycler.setHasFixedSize(true);
        languageRecycler.setAdapter(new LanguageAdapter(this, languages));
    }

    @Override
    public void onGetLanguages(LanguagesResponse languagesResponse) {
        iniRecyclerAdapter(languagesResponse.getLanguage());
    }

    @Override
    public void onChooseLanguage() {
        Intent signIntent = new Intent(this, SignInActivity.class);
        startActivity(signIntent);
    }

}
