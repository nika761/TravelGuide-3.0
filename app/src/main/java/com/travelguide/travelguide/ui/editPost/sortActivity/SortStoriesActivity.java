package com.travelguide.travelguide.ui.editPost.sortActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.travelguide.travelguide.R;
import com.travelguide.travelguide.model.ItemMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.travelguide.travelguide.ui.editPost.EditPostActivity.STORIES_PATHS;

public class SortStoriesActivity extends AppCompatActivity implements View.OnClickListener {

    private List<ItemMedia> itemMedia = new ArrayList<>();
    private TextView doneBtn;
    private ImageButton backBtn;
    private SortStoriesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_stories);

        this.itemMedia = (List<ItemMedia>) getIntent().getSerializableExtra(STORIES_PATHS);

        initUI();
        initRecycler();
    }

    private void initUI() {
        doneBtn = findViewById(R.id.sort_stories_done_btn);
        doneBtn.setOnClickListener(this);

        backBtn = findViewById(R.id.sort_stories_back_btn);
        backBtn.setOnClickListener(this);
    }

    private void initRecycler() {
        RecyclerView recyclerSortStories = findViewById(R.id.stories_sort_recycler);
        adapter = new SortStoriesAdapter(itemMedia);
        recyclerSortStories.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerSortStories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerSortStories.setHasFixedSize(true);
        recyclerSortStories.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getLayoutPosition();
                int toPosition = target.getLayoutPosition();
                Collections.swap(itemMedia, fromPosition, toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                recyclerView.getAdapter().notifyDataSetChanged();

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerSortStories);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sort_stories_done_btn:
                Intent resultIntent = new Intent();
                resultIntent.putExtra(STORIES_PATHS, (Serializable) itemMedia);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;

            case R.id.sort_stories_back_btn:
                finish();
                break;
        }
    }
}
