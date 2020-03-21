package com.example.travelguide.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.Utils;

import java.util.List;


public class ChooseLanguageActivity extends AppCompatActivity {

    private TextView englishFull, georgianFull, russianFull, chineseFull;
    private Button startButton;
    private List<User> currentUsers;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        iniUI();
        setListeners();
    }

    private void iniUI() {
        englishFull = findViewById(R.id.english_full);
        georgianFull = findViewById(R.id.georgian_full);
        russianFull = findViewById(R.id.russian_full);
        chineseFull = findViewById(R.id.chinese_full);
        startButton = findViewById(R.id.start_button);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setListeners() {

        englishFull.setOnClickListener(v -> {
            englishFull.setTextColor(getColor(R.color.yellowTextView));
            georgianFull.setTextColor(getColor(R.color.white));
            russianFull.setTextColor(getColor(R.color.white));
            chineseFull.setTextColor(getColor(R.color.white));
            startButton.setTextColor(getColor(R.color.yellowTextView));
            startButton.setEnabled(true);
        });

        georgianFull.setOnClickListener(v -> {
            englishFull.setTextColor(getColor(R.color.white));
            georgianFull.setTextColor(getColor(R.color.yellowTextView));
            russianFull.setTextColor(getColor(R.color.white));
            chineseFull.setTextColor(getColor(R.color.white));
            startButton.setTextColor(getColor(R.color.yellowTextView));
            startButton.setEnabled(true);
        });

        russianFull.setOnClickListener(v -> {
            englishFull.setTextColor(getColor(R.color.white));
            georgianFull.setTextColor(getColor(R.color.white));
            russianFull.setTextColor(getColor(R.color.yellowTextView));
            chineseFull.setTextColor(getColor(R.color.white));
            startButton.setTextColor(getColor(R.color.yellowTextView));
            startButton.setEnabled(true);
        });

        chineseFull.setOnClickListener(v -> {
            englishFull.setTextColor(getColor(R.color.white));
            georgianFull.setTextColor(getColor(R.color.white));
            russianFull.setTextColor(getColor(R.color.white));
            chineseFull.setTextColor(getColor(R.color.yellowTextView));
            startButton.setTextColor(getColor(R.color.yellowTextView));
            startButton.setEnabled(true);
        });

        startButton.setOnClickListener(v -> {
            currentUsers = Utils.getSavedUsers(this);
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
        });
    }

    private void startUserPageActivity(User currentUser) {
        String firstName = currentUser.getName();
        String lastName = currentUser.getLastName();
        String url = currentUser.getUrl();
        String id = currentUser.getId();
        String email = currentUser.getEmail();
        String loginType = currentUser.getLoginType();

        Intent userPageIntent = new Intent(this, UserPageActivity.class);
        userPageIntent.putExtra("name", firstName);
        userPageIntent.putExtra("lastName", lastName);
        userPageIntent.putExtra("email", email);
        userPageIntent.putExtra("url", url);
        userPageIntent.putExtra("id", id);
        userPageIntent.putExtra("loginType", loginType);
        startActivity(userPageIntent);
    }
}
