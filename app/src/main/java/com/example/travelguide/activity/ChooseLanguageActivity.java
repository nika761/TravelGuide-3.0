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
import com.example.travelguide.utils.Utils;


public class ChooseLanguageActivity extends AppCompatActivity {

    private TextView englishFull, georgianFull, russianFull, chineseFull;
    private Button startButton;

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
            Intent intent = new Intent(this, SignInActivity.class);
            if (Utils.getSavedUsers(this).size() > 0) {
                intent = new Intent(ChooseLanguageActivity.this, SavedUserActivity.class);
            }
            startActivity(intent);
        });
    }
}
