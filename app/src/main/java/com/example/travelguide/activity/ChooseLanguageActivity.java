package com.example.travelguide.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.LanguagesAdapter;
import com.example.travelguide.interfaces.ILanguageActivity;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.model.User;
import com.example.travelguide.presenters.LanguagePresenter;
import com.example.travelguide.utils.UtilsPref;

import java.util.List;


public class ChooseLanguageActivity extends AppCompatActivity implements ILanguageActivity {

    private TextView englishFull, georgianFull, russianFull, englishSmall, georgianSmall, russianSmall;
    private Button startButton;
    private List<User> currentUsers;
    private RecyclerView recyclerView;
    private LanguagePresenter languagePresenter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        recyclerView = findViewById(R.id.language_recycler);
        iniUI();
        languagePresenter = new LanguagePresenter(this);
        languagePresenter.sentLanguageRequest();
//        setListeners();
//        setAnim();
    }

    private void iniUI() {
//        englishFull = findViewById(R.id.english_full);
//        georgianFull = findViewById(R.id.georgian_full);
//        russianFull = findViewById(R.id.russian_full);
//        englishSmall = findViewById(R.id.english_small);
//        georgianSmall = findViewById(R.id.georgian_small);
//        russianSmall = findViewById(R.id.russian_small);
//        startButton = findViewById(R.id.start_button);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setListeners() {

//        englishFull.setOnClickListener(v -> {
//            englishFull.setTextColor(getColor(R.color.yellowTextView));
//            georgianFull.setTextColor(getColor(R.color.white));
//            russianFull.setTextColor(getColor(R.color.white));
//            startButton.setTextColor(getColor(R.color.yellowTextView));
//            startButton.setEnabled(true);
//        });
//
//        georgianFull.setOnClickListener(v -> {
//            englishFull.setTextColor(getColor(R.color.white));
//            georgianFull.setTextColor(getColor(R.color.yellowTextView));
//            russianFull.setTextColor(getColor(R.color.white));
//            startButton.setTextColor(getColor(R.color.yellowTextView));
//            startButton.setEnabled(true);
//        });
//
//        russianFull.setOnClickListener(v -> {
//            englishFull.setTextColor(getColor(R.color.white));
//            georgianFull.setTextColor(getColor(R.color.white));
//            russianFull.setTextColor(getColor(R.color.yellowTextView));
//            startButton.setTextColor(getColor(R.color.yellowTextView));
//            startButton.setEnabled(true);
//        });
        startButton.setOnClickListener(v -> checkSavedUsers());

    }


    public void checkSavedUsers() {
        currentUsers = UtilsPref.getSavedUsers(this);
        if (currentUsers.size() != 0) {
            for (User currentUser : currentUsers) {
                if (currentUser != null && currentUser.getLoginType() != null
                        && currentUser.getLoginType().equals("google")) {
                    startUserPageActivity(currentUser);
                } else if (currentUser != null && currentUser.getLoginType() != null
                        && currentUser.getLoginType().equals("facebook")) {
                    startUserPageActivity(currentUser);
                } else {
                    Intent userIntent = new Intent(this, SavedUserActivity.class);
                    startActivity(userIntent);
                }
            }
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

    private void setAnim() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_languages);
        Animation animationSecond = AnimationUtils.loadAnimation(this, R.anim.animation_languages_two);
        Animation animationThird = AnimationUtils.loadAnimation(this, R.anim.animation_languages_three);
        englishFull.setAnimation(animation);
        georgianFull.setAnimation(animationSecond);
        russianFull.setAnimation(animationThird);
        englishSmall.setAnimation(animation);
        georgianSmall.setAnimation(animationSecond);
        russianSmall.setAnimation(animationThird);

    }

    @Override
    public void onGetLanguages(LanguagesResponseModel languagesResponseModel) {
        iniRecyclerAdapter(languagesResponseModel.getLanguage());
    }
}
