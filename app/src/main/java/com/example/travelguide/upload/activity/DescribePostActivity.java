package com.example.travelguide.upload.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.upload.adapter.recycler.DescribePostAdapter;

import java.util.ArrayList;

public class DescribePostActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<String> storiesPath = new ArrayList<>();
    private ImageButton backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_post);
        this.storiesPath = getIntent().getStringArrayListExtra("stories");
        backBtn = findViewById(R.id.describe_post_back_btn);
        backBtn.setOnClickListener(this);
        initStoriesDescribeRecycler(storiesPath);

    }

    private void initStoriesDescribeRecycler(ArrayList<String> storiesPath) {
        RecyclerView recyclerDescribeStories = findViewById(R.id.describe_post_recycler);
        DescribePostAdapter adapter = new DescribePostAdapter(storiesPath);
        recyclerDescribeStories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerDescribeStories.setHasFixedSize(true);
        recyclerDescribeStories.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.describe_post_back_btn:
                finish();
                break;
        }
    }
}
