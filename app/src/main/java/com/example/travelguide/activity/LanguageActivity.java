package com.example.travelguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.LanguagesAdapter;
import com.example.travelguide.interfaces.ILanguageActivity;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.model.User;
import com.example.travelguide.presenters.LanguagePresenter;
import com.example.travelguide.utils.UtilsPref;

import java.util.List;


public class LanguageActivity extends AppCompatActivity implements ILanguageActivity {

    private Button startButton;
    private List<User> currentUsers;
    private RecyclerView recyclerView;
    private LanguagePresenter languagePresenter;
    private List<LanguagesResponseModel.Language> languages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        recyclerView = findViewById(R.id.language_recycler);
        languagePresenter = new LanguagePresenter(this);
//        languagePresenter.sentLanguageRequest();
        String INTENT_LANGUAGES = "languages";
        languages = (List<LanguagesResponseModel.Language>) getIntent().getSerializableExtra(INTENT_LANGUAGES);
        if (languages != null) {
            iniRecyclerAdapter(languages);
        }
    }

    public void checkSavedUsers() {
        currentUsers = UtilsPref.getSavedUsers(this);
        if (currentUsers.size() != 0) {
//            for (User currentUser : currentUsers) {
//                if (currentUser != null && currentUser.getLoginType() != null
//                        && currentUser.getLoginType().equals("google")) {
//                    startUserPageActivity(currentUser);
//                } else if (currentUser != null && currentUser.getLoginType() != null
//                        && currentUser.getLoginType().equals("facebook")) {
//                    startUserPageActivity(currentUser);
//                } else {
            Intent userIntent = new Intent(this, SavedUserActivity.class);
            startActivity(userIntent);
//                }
//            }
        } else {
            Intent signIntent = new Intent(this, SignInActivity.class);
            startActivity(signIntent);
        }
    }

    private void startUserPageActivity(User currentUser) {
        Intent userPageIntent = new Intent(this, UserPageActivity.class);
        userPageIntent.putExtra("name", currentUser.getName());
        userPageIntent.putExtra("lastName", currentUser.getLastName());
        userPageIntent.putExtra("email", currentUser.getEmail());
        userPageIntent.putExtra("url", currentUser.getUrl());
        userPageIntent.putExtra("id", currentUser.getId());
        userPageIntent.putExtra("loginType", currentUser.getLoginType());
        startActivity(userPageIntent);
    }

    private void iniRecyclerAdapter(List updatedLanguagesList) {
        LanguagesAdapter adapter = new LanguagesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setLanguageList(updatedLanguagesList);
    }

    @Override
    public void onGetLanguages(LanguagesResponseModel languagesResponseModel) {
        iniRecyclerAdapter(languagesResponseModel.getLanguage());
    }
}
