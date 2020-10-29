package com.example.travelguide.ui.home.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.travelguide.R;
import com.example.travelguide.helper.StoryView;
import com.example.travelguide.model.response.PostResponse;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<PostResponse.Posts> posts;
    private HomeFragmentListener homeFragmentListener;
    private Context context;
    private int postId = -1;
    private StoryAdapter storyRecyclerAdapter;

    PostAdapter(HomeFragmentListener homeFragmentListener, Context context, List<PostResponse.Posts> posts) {
        this.homeFragmentListener = homeFragmentListener;
        this.context = context;
        this.posts = posts;
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
            storyRecyclerAdapter.setStories(posts.get(position).getPost_stories());
            storyRecyclerAdapter.setCurrentPost(posts.get(position));
            storyRecyclerAdapter.setStoryView(holder.storyView);
            postId = posts.get(position).getPost_id();
//            homeFragmentListener.startTimer(posts.get(position).getPost_id());
            Log.e("postsdsdsd", posts.get(position).getPost_id() + "post id from post");
        } else {
            Toast.makeText(context, "NO STORY", Toast.LENGTH_SHORT).show();
        }
    }

    int getPostId() {
        return postId;
    }

    void setPosts(List<PostResponse.Posts> posts) {
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    public StoryAdapter getStoryRecyclerAdapter() {
        return storyRecyclerAdapter;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder implements StoryView.StoryListener {
        public RecyclerView recyclerView;
        private LinearLayoutManager layoutManager;
        public StoryView storyView;
        int oldPosition;

        PostHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.post_recycler_new);
            storyView = itemView.findViewById(R.id.story_container_new_new);
            storyRecyclerAdapter = new StoryAdapter(homeFragmentListener);
            storyView.setListener(this);
            initRecycler(recyclerView);
        }

        void iniStory(int position) {
            storyView.setStorySize(posts.get(position).getPost_stories().size());
//            int duration = posts.get(position).getPost_stories().get(0).getSecond();
//            storyView.start(0, duration + 2000);
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
                        storyView.start(firstVisibleItem, posts.get(getLayoutPosition()).getPost_stories().get(firstVisibleItem).getSecond() + 2000);
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
