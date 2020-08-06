package com.example.travelguide.ui.home.adapter.recycler;

import android.content.Context;
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
import com.example.travelguide.ui.home.fragment.StoryView;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;
import com.example.travelguide.model.response.PostResponse;

import java.util.List;


public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostHolder> {

    private List<PostResponse.Posts> posts;
    private OnLoadFinishListener onLoadFinishListener;
    private Context context;


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
            storyRecyclerAdapter = new StoryRecyclerAdapter(onLoadFinishListener, posts.get(getLayoutPosition()+1));
            storyView.setListener(this);
            initRecycler(recyclerView);
        }

        public void iniStory(int position) {
            storyView.setStorySize(posts.get(position).getPost_stories().size());
            storyView.start(0, 10000);
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

                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                        storyView.stop(true);
                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        storyView.stop(true);
                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        storyView.stop(false);
                    }

                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int firstVisibleItem = layoutManager.findLastVisibleItemPosition();

                    if (oldPosition != firstVisibleItem) {
                        storyView.start(firstVisibleItem, 10000);
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
//            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(linearLayout.getChildAt(1),
//                    "progress", 0, 100);
//            objectAnimator.setDuration(TimeUnit.SECONDS.toMillis((long) duration));
//            objectAnimator.setInterpolator(new LinearInterpolator());
//            objectAnimator.start();

//        private void setViewPager(ViewPager viewPager) {
//            StoryPagerAdapter storyPagerAdapter = new StoryPagerAdapter(fragmentManager);
//            int size = posts.get(getLayoutPosition()).getPost_stories().size();
//            for (int i = 0; i < size; i++) {
//                storyPagerAdapter.addFragment(newInstance(posts.get(getLayoutPosition()).getPost_stories().get(i).getUrl()
//                        , posts.get(getLayoutPosition()).getPost_stories().get(i).getSecond()));
//            }
//            viewPager.setAdapter(storyPagerAdapter);
//            for (int i = 0; i < size; i++) {
//                tabLayout.addTab(tabLayout.newTab());
//            }
//            tabLayout.setupWithViewPager(viewPager);
//
//            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    onLoadFinishListener.onStorySelected(position);
//                    StoryPagerAdapter storyPagerAdapter1 = (StoryPagerAdapter) viewPager.getAdapter();
//                    StoryFragment storyFragment = (StoryFragment) storyPagerAdapter1.getItem(position);
//                    storyFragment.stopVideo();
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//
//        }
//
//        StoryFragment newInstance(String url, int duration) {
//            StoryFragment myFragment = new StoryFragment();
//
//            Bundle args = new Bundle();
//            args.putString("url", url);
//            args.putInt("duration", duration);
//            myFragment.setArguments(args);
//            return myFragment;
//        }
    }
}
