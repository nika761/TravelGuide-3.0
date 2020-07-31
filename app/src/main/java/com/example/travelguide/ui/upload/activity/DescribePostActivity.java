package com.example.travelguide.ui.upload.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.model.ItemMedia;

import java.util.ArrayList;
import java.util.List;

import static com.example.travelguide.ui.upload.activity.EditPostActivity.STORIES_PATHS;

public class DescribePostActivity extends AppCompatActivity implements View.OnClickListener {
    private List<ItemMedia> itemMedia = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_post);
        this.itemMedia = (List<ItemMedia>) getIntent().getSerializableExtra(STORIES_PATHS);
        initUI();
    }

    private void initUI() {
        ImageButton backBtn = findViewById(R.id.describe_post_back_btn);
        backBtn.setOnClickListener(this);

        View selectCover = findViewById(R.id.select_cover_btn);
        selectCover.setOnClickListener(this);
    }

//    private void initStoriesDescribeRecycler(ArrayList<String> storiesPath) {
//        RecyclerView recyclerDescribeStories = findViewById(R.id.describe_post_recycler);
//        DescribePostAdapter adapter = new DescribePostAdapter(storiesPath);
//        recyclerDescribeStories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        recyclerDescribeStories.setHasFixedSize(true);
//        recyclerDescribeStories.setAdapter(adapter);
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.describe_post_back_btn:
                finish();
                break;

            case R.id.select_cover_btn:

                break;
        }
    }
}
