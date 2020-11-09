package com.travel.guide.ui.home.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.travel.guide.R;
import com.travel.guide.helper.custom.StoryView;
import com.travel.guide.model.response.PostResponse;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<PostResponse.Posts> posts;
    private HomeFragmentListener homeFragmentListener;

    private PostHolder postHolder;
    private int postHolderPosition;

    PostAdapter(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        this.postHolder = holder;
        this.postHolderPosition = position;

        holder.initStoryAdapter(position);

        holder.loadMoreCallback(position);
    }

    void setPosts(List<PostResponse.Posts> posts) {

        if (this.posts != null && this.posts.size() != 0)
            this.posts.addAll(posts);

        else {
            this.posts = posts;
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder implements StoryView.StoryListener, StoryPlayingListener {

        private LinearLayoutManager layoutManager;
        public RecyclerView storyRecycler;
        public StoryAdapter storyAdapter;

        public StoryView storyView;

        int oldPosition;

        PostHolder(@NonNull View itemView) {
            super(itemView);
            storyRecycler = itemView.findViewById(R.id.post_recycler_new);

            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(storyRecycler);

            storyView = itemView.findViewById(R.id.story_container_new_new);

            storyView.setListener(this);

        }

        void iniStory(int position) {
            storyView.setStorySize(posts.get(position).getPost_stories().size());
//            int duration = posts.get(position).getPost_stories().get(0).getSecond();
//            storyView.start(0, duration + 2000);
        }

        void delete(int position) {
//            StoryAdapter.StoryHolder oldHolder = ((StoryAdapter.StoryHolder) storyRecycler.findViewHolderForAdapterPosition(position));
//            oldHolder.removeVideoView();

            StoryAdapter.StoryHolder storyHolder = ((StoryAdapter.StoryHolder) storyRecycler.findViewHolderForAdapterPosition(position));
            if (storyHolder != null) {
                storyHolder.stopVideo();
            }
        }

        void play(int position) {
            StoryAdapter.StoryHolder storyHolder = ((StoryAdapter.StoryHolder) storyRecycler.findViewHolderForAdapterPosition(position));
            if (storyHolder != null) {
                storyHolder.playVideo(position);
            }
        }


        @Override
        public void storyFinished(int finishedPosition) {
            if (layoutManager.findLastVisibleItemPosition() == storyView.size - 1) {
                storyRecycler.post(() -> storyRecycler.smoothScrollToPosition(0));
            } else {
                storyRecycler.post(() -> storyRecycler.smoothScrollToPosition(finishedPosition + 1));
            }
        }

        void loadMoreCallback(int position) {
            if (position == posts.size() - 2) {
                homeFragmentListener.onLazyLoad(posts.get(position).getPost_id());
            }
        }

        void initStoryAdapter(int position) {

            layoutManager = new LinearLayoutManager(storyRecycler.getContext(), RecyclerView.HORIZONTAL, false);
            storyRecycler.setLayoutManager(layoutManager);

            storyAdapter = new StoryAdapter(homeFragmentListener, this);
            storyAdapter.setStories(posts.get(position).getPost_stories());
            storyAdapter.setCurrentPost(posts.get(position));
            storyAdapter.setStoryView(storyView);

            storyRecycler.setAdapter(storyAdapter);

            storyRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    switch (newState) {

                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                            storyView.stop(true);
                            break;

                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            storyView.stop(false);
                            break;

                    }
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

            Log.e("lazyLoad", "post id " + posts.get(position).getPost_id());

        }

        @Override
        public void onGetStoryHolder(StoryAdapter.StoryHolder storyHolder, int storyHolderPosition) {
            homeFragmentListener.onGetHolder(storyRecycler, storyHolder, storyHolderPosition, postHolder, postHolderPosition);
        }
    }

}



