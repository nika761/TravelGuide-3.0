package com.example.travelguide.ui.home.adapter.recycler;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.travelguide.R;
import com.example.travelguide.ui.home.fragment.StoryView;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;
import com.example.travelguide.model.response.PostResponse;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostHolder> {

    private List<PostResponse.Posts> posts;
    private OnLoadFinishListener onLoadFinishListener;
    private Context context;
    private int postPosition;

    public PostRecyclerAdapter(OnLoadFinishListener onLoadFinishListener, List<PostResponse.Posts> posts, Context context) {
        this.posts = posts;
        this.onLoadFinishListener = onLoadFinishListener;
        this.context = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        if (posts.get(position).getPost_stories() != null) {
            holder.storyRecyclerAdapter.setStories(posts.get(position).getPost_stories());
            onLoadFinishListener.startTimer(posts.get(position).getPost_id());
            Log.e("mmmmnjasdasdasjjj", position + " " + posts.get(position).getPost_id());

            postPosition = position;
        } else {
            Toast.makeText(context, "NO STORY", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder implements StoryView.StoryListener {
        public RecyclerView recyclerView;
        private StoryRecyclerAdapter storyRecyclerAdapter;
        private LinearLayoutManager layoutManager;
        public StoryView storyView;
        int oldPosition;

        PostHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.post_recycler_new);
            storyView = itemView.findViewById(R.id.story_container_new_new);
            storyRecyclerAdapter = new StoryRecyclerAdapter(onLoadFinishListener, posts.get(postPosition));
            storyView.setListener(this);
            initRecycler(recyclerView);
        }

        public void iniStory(int position) {
            storyView.setStorySize(posts.get(position).getPost_stories().size());
            int duration = posts.get(position).getPost_stories().get(0).getSecond();
            storyView.start(0, duration * 1000);
        }

        private void initRecycler(RecyclerView storiesRecycler) {
            layoutManager = new LinearLayoutManager(storiesRecycler.getContext(),
                    RecyclerView.HORIZONTAL, false);
            storiesRecycler.setLayoutManager(layoutManager);
//            videoPlayerRecyclerView.setStories(stories);
            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(storiesRecycler);
            storiesRecycler.setAdapter(storyRecyclerAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    switch (newState) {

                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                            storyView.stop(true);
                            break;

                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                            storyView.stop(true);
                            break;

                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            storyView.stop(false);
                            break;

                    }

//                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                        storyView.stop(true);
//                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                        storyView.stop(true);
//                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                        storyView.stop(false);
//                    }

                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int firstVisibleItem = layoutManager.findLastVisibleItemPosition();

                    if (oldPosition != firstVisibleItem) {
                        storyView.start(firstVisibleItem, posts.get(getLayoutPosition()).getPost_stories().get(getLayoutPosition()).getSecond() * 1000);
                        oldPosition = firstVisibleItem;
                    }

                }
            });
        }

        @Override
        public void storyFinished(int finishedPosition) {
            if (layoutManager.findLastVisibleItemPosition() == storyView.size - 1) {
                recyclerView.post(() -> recyclerView.smoothScrollToPosition(0));

            } else {
                recyclerView.post(() -> recyclerView.smoothScrollToPosition(finishedPosition + 1));
            }
        }
    }
}
