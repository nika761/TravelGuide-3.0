package com.example.travelguide.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;

public class SearchActivity extends AppCompatActivity {

    private ImageButton searchBtn;
    private TextView explore;
    private View searchContainerFirst, searchContainerSecond, searchLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUI();
        setClickListeners();
    }

    private void initUI() {
        searchBtn = findViewById(R.id.search_search_btn);
        explore = findViewById(R.id.search_explore);
        searchContainerFirst = findViewById(R.id.search_container_first);
        searchContainerSecond = findViewById(R.id.search_container_second);
        searchLine = findViewById(R.id.search_line);
    }

    private void setClickListeners() {
        searchBtn.setOnClickListener(v -> {
            explore.setVisibility(View.GONE);
            searchContainerFirst.setVisibility(View.GONE);
            searchContainerSecond.setVisibility(View.VISIBLE);
            searchLine.setVisibility(View.VISIBLE);
        });
    }
}
