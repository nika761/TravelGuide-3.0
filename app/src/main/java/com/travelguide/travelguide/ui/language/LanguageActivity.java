package com.travelguide.travelguide.ui.language;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travelguide.travelguide.R;
import com.travelguide.travelguide.ui.login.signIn.SignInActivity;
import com.travelguide.travelguide.model.response.LanguagesResponse;

import java.util.List;

import static com.travelguide.travelguide.ui.splashScreen.SplashScreenActivity.INTENT_LANGUAGES;


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
