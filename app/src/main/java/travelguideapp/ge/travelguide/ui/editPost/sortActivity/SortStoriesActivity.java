package travelguideapp.ge.travelguide.ui.editPost.sortActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.customModel.ItemMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static travelguideapp.ge.travelguide.ui.editPost.EditPostActivity.STORIES_PATHS;

public class SortStoriesActivity extends AppCompatActivity {

    private List<ItemMedia> itemMedia = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_stories);

        this.itemMedia = (List<ItemMedia>) getIntent().getSerializableExtra(STORIES_PATHS);

        initUI();
        initRecycler();
    }

    private void initUI() {
        TextView doneBtn = findViewById(R.id.sort_stories_done_btn);
        doneBtn.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(STORIES_PATHS, (Serializable) itemMedia);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        ImageButton backBtn = findViewById(R.id.sort_stories_back_btn);
        backBtn.setOnClickListener(v -> finish());

    }

    private void initRecycler() {
        RecyclerView recyclerSortStories = findViewById(R.id.stories_sort_recycler);
        SortStoriesAdapter adapter = new SortStoriesAdapter(itemMedia);

        recyclerSortStories.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerSortStories.setHasFixedSize(true);
        recyclerSortStories.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

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
}
