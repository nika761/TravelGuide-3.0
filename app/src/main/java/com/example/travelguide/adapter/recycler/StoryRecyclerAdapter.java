package com.example.travelguide.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.RequestManager;
import com.example.travelguide.R;
import com.example.travelguide.interfaces.OnStoryChangeListener;
import com.example.travelguide.model.response.PostResponseModel;
import com.example.travelguide.helper.VideoPlayerRecyclerView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.StoryHolder> {

    private List<PostResponseModel.Posts> posts;
    private List<PostResponseModel.Post_stories> stories;
    private FragmentManager fragmentManager;
    private OnStoryChangeListener onStoryChangeListener;
    private int previousPosition;

    public StoryRecyclerAdapter(OnStoryChangeListener onStoryChangeListener, List<PostResponseModel.Posts> posts) {
        this.posts = posts;
        this.onStoryChangeListener = onStoryChangeListener;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_recycler, parent, false);
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
//        holder.setViewPager(holder.viewPager);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class StoryHolder extends RecyclerView.ViewHolder {
        private ViewPager viewPager;
        private TabLayout tabLayout;
        private VideoPlayerRecyclerView videoPlayerRecyclerView;
        private RequestManager requestManager;


        StoryHolder(@NonNull View itemView) {
            super(itemView);
            videoPlayerRecyclerView = itemView.findViewById(R.id.post_recycler);
            initRecycler(videoPlayerRecyclerView, posts.get(getLayoutPosition() +1).getPost_stories());
//            viewPager = itemView.findViewById(R.id.view_pager_test);
//            tabLayout = itemView.findViewById(R.id.tab_test_story);
        }

        private void initRecycler(VideoPlayerRecyclerView videoPlayerRecyclerView, List<PostResponseModel.Post_stories> stories) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(videoPlayerRecyclerView.getContext(),
                    RecyclerView.HORIZONTAL, false);
            videoPlayerRecyclerView.setLayoutManager(layoutManager);
//            stories = posts.get(getLayoutPosition()+1).getPost_stories();
            videoPlayerRecyclerView.setStories(stories);
            VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(stories);
            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(videoPlayerRecyclerView);
            videoPlayerRecyclerView.setAdapter(adapter);
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
