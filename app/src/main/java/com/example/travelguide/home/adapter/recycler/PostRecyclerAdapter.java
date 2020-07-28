package com.example.travelguide.home.adapter.recycler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.travelguide.R;
import com.example.travelguide.home.interfaces.OnStoryChangeListener;
import com.example.travelguide.model.response.PostResponseModel;

import java.util.List;


public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostHolder> {

    private List<PostResponseModel.Posts> posts;
    private OnStoryChangeListener onStoryChangeListener;
    private Context context;

    public PostRecyclerAdapter(OnStoryChangeListener onStoryChangeListener, List<PostResponseModel.Posts> posts, Context context) {
        this.posts = posts;
        this.onStoryChangeListener = onStoryChangeListener;
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
//            holder.iniStoryBar(posts.get(position).getPost_stories().size());
            if (posts.get(position).getPost_stories().size() > 0) {
//                holder.storiesProgressView.setStoriesCount(posts.get(position).getPost_stories().size());
//                holder.storiesProgressView.setStoryDuration(10000);
//                holder.storiesProgressView.startStories();
            }
        } else {
            Toast.makeText(context, "NO STORY", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class PostHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private LinearLayout linearLayout;
        private StoryRecyclerAdapter storyRecyclerAdapter;
        private ObjectAnimator objectAnimator;
        private ProgressBar progressBar;

        PostHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.post_recycler_new);
            linearLayout = itemView.findViewById(R.id.story_container_new_new);
            storyRecyclerAdapter = new StoryRecyclerAdapter(onStoryChangeListener);
            initRecycler(recyclerView);
        }

        private void initRecycler(RecyclerView videoPlayerRecyclerView) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(videoPlayerRecyclerView.getContext(),
                    RecyclerView.HORIZONTAL, false);
            videoPlayerRecyclerView.setLayoutManager(layoutManager);
//            videoPlayerRecyclerView.setStories(stories);
            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(videoPlayerRecyclerView);
            videoPlayerRecyclerView.setAdapter(storyRecyclerAdapter);
        }

        private void iniStoryBar(int count) {
            linearLayout.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    5,
                    1.0f);
            Drawable storyBarItemBg = context.getResources().getDrawable(R.drawable.story_bar_item_bg, null);
            params.leftMargin = 3;
            params.rightMargin = 3;
            for (int i = 0; i < count; i++) {
                progressBar = new ProgressBar(context,
                        null,
                        android.R.attr.progressBarStyleHorizontal);
                progressBar.setProgressDrawable(storyBarItemBg);
                progressBar.setLayoutParams(params);
                linearLayout.addView(progressBar);
            }

            objectAnimator = new ObjectAnimator();
            objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
            objectAnimator.setDuration(10000);
            objectAnimator.setInterpolator(new LinearInterpolator());
            objectAnimator.start();

            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
        }

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
//                    onStoryChangeListener.onStorySelected(position);
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
