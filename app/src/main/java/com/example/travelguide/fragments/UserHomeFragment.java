package com.example.travelguide.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.PostAdapter;
import com.example.travelguide.adapter.recycler.StoryRecyclerAdapter;
import com.example.travelguide.helper.HelperMedia;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.interfaces.IHomeFragment;
import com.example.travelguide.interfaces.OnStoryChangeListener;
import com.example.travelguide.model.Post;
import com.example.travelguide.model.request.PostRequestModel;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.model.response.PostResponseModel;
import com.example.travelguide.presenters.HomePresenter;
import com.example.travelguide.helper.VideoPlayerRecyclerView;
import com.genius.multiprogressbar.MultiProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class UserHomeFragment extends Fragment implements IHomeFragment ,OnStoryChangeListener {
    private ViewPager2 viewPager2;
    private int oldPosition;
    private int currentPosition;
    private List<Post> posts = new ArrayList<>();
    private VideoPlayerRecyclerView videoPlayerRecyclerView;
    private RecyclerView recyclerView;
    private HomePresenter homePresenter;
    private Context context;
    private LinearLayout storyContainer;
    private PostResponseModel postResponseModel;
    private StoriesProgressView storiesProgressView;
    private MultiProgressBar multiProgressBar;
    private int currentItemPosition = 0;
    private int storyPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        View decorView = window.getDecorView();
//        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewPager2 = view.findViewById(R.id.post_view_pager);
//        videoPlayerRecyclerView = view.findViewById(R.id.recycler_view);
        homePresenter = new HomePresenter(this);
        recyclerView = view.findViewById(R.id.recycler_story);
        storyContainer = view.findViewById(R.id.story_container);
        storiesProgressView = view.findViewById(R.id.story_progressview);
        multiProgressBar = view.findViewById(R.id.multiprogressbar);
        PostRequestModel postRequestModel = new PostRequestModel(29);
        homePresenter.getPosts("Bearer" + " " + HelperPref.getCurrentAccessToken(context), postRequestModel);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setPostsData() {
        String path = "/storage/emulated/0/DCIM/Camera/2020-07-12-104752811.mp4";

        ArrayList<String> arrayList = HelperMedia.getVideosPathByDate(viewPager2.getContext());
        viewPager2.setAdapter(new PostAdapter(arrayList));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                scrollListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void scrollListener(int position) {
        oldPosition = currentPosition;
        currentPosition = position;
        if (oldPosition > currentPosition) {
            Log.e("scroll", String.valueOf(position));
            Log.e("scroll", String.valueOf(oldPosition));
        } else {
            Log.e("scroll", String.valueOf(position));
            Log.e("scroll", String.valueOf(currentPosition));
        }
    }

    private void onGetData() {
        if (getArguments() != null && getArguments().containsKey("user")) {
            LoginResponseModel.User serverUser = (LoginResponseModel.User) getArguments().getSerializable("user");
            if (serverUser != null) {
            }
//            User user = (User) getArguments().getSerializable("user");
//            if (user != null)
//                setUserData(user);
        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView(List<PostResponseModel.Posts> posts) {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        videoPlayerRecyclerView.setLayoutManager(layoutManager);
//        ArrayList<String> mediaObjects = HelperMedia.getVideosPathByDate(getContext());
//        videoPlayerRecyclerView.setMediaObjects(mediaObjects);
//        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(mediaObjects, initGlide());
//        SnapHelper helper = new PagerSnapHelper();
//        helper.attachToRecyclerView(videoPlayerRecyclerView);
//        videoPlayerRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        StoryRecyclerAdapter adapter = new StoryRecyclerAdapter(this, posts);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (postResponseModel != null && currentItemPosition != layoutManager.findFirstVisibleItemPosition()) {
//                    currentItemPosition = layoutManager.findFirstVisibleItemPosition();
//                    iniStoryBar(postResponseModel.getPosts().get(layoutManager.findFirstVisibleItemPosition()).getPost_stories().size());
//                }
//            }
//        });

    }

    private RequestManager initGlide() {

        RequestOptions options = new RequestOptions();
        return Glide.with(this).setDefaultRequestOptions(options);

    }

    @Override
    public void onPause() {
        if (videoPlayerRecyclerView != null)
            videoPlayerRecyclerView.releasePlayer();
        super.onPause();
    }

    @Override
    public void onGetPosts(PostResponseModel postResponseModel) {
        if (postResponseModel.getStatus() == 0) {
            initRecyclerView(postResponseModel.getPosts());
            this.postResponseModel = postResponseModel;
//            storiesProgressView.setStoriesCount(postResponseModel.getPosts().get(0).getPost_stories().size());
//            storiesProgressView.setStoryDuration(10000);
//            storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
//                @Override
//                public void onNext() {
//
//                }
//
//                @Override
//                public void onPrev() {
//
//                }
//
//                @Override
//                public void onComplete() {
//
//                }
//            });
////            storiesProgressView.startStories();
//
//            multiProgressBar.setProgressStepsCount(postResponseModel.getPosts().get(0).getPost_stories().size());
//
//            multiProgressBar.start();

//            iniStoryBar(postResponseModel.getPosts().get(0).getPost_stories().size());
        }
    }


    private void iniStoryBar(int count) {
        storyContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                5,
                1.0f);
        Drawable storyBarItemBg = getResources().getDrawable(R.drawable.story_bar_item_bg, null);
        params.leftMargin = 3;
        params.rightMargin = 3;
        for (int i = 0; i < count; i++) {
            ProgressBar progressBar = new ProgressBar(context,
                    null,
                    android.R.attr.progressBarStyleHorizontal);
            progressBar.setProgressDrawable(storyBarItemBg);
            progressBar.setLayoutParams(params);
            storyContainer.addView(progressBar);

        }
    }


    @Override
    public void onStorySelected(int position) {
        if (position < storyPosition){
            storiesProgressView.reverse();
        }else {
            storiesProgressView.skip();
        }
        storyPosition = position;

    }

    @Override
    public void onPostChanged(int position) {

    }
}
