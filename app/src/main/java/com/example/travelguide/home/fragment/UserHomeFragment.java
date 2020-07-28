package com.example.travelguide.home.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.travelguide.R;
import com.example.travelguide.home.adapter.recycler.PostRecyclerAdapter;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.home.interfaces.IHomeFragment;
import com.example.travelguide.home.interfaces.OnStoryChangeListener;
import com.example.travelguide.model.request.PostRequestModel;
import com.example.travelguide.model.response.PostResponseModel;
import com.example.travelguide.home.presenter.HomePresenter;

import java.util.List;
import java.util.Objects;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class UserHomeFragment extends Fragment implements IHomeFragment, OnStoryChangeListener {
    private RecyclerView recyclerView;
    private HomePresenter homePresenter;
    private Context context;
    private LinearLayout storyContainer;
    private StoriesProgressView storiesProgressView;
    private int storyPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = view.findViewById(R.id.recycler_story);
        storiesProgressView = view.findViewById(R.id.stories_progress);

//        View decorView = window.getDecorView();
//        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homePresenter = new HomePresenter(this);
        PostRequestModel postRequestModel = new PostRequestModel(29);
        homePresenter.getPosts("Bearer" + " " + HelperPref.getCurrentAccessToken(context), postRequestModel);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initRecyclerView(List<PostResponseModel.Posts> posts) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(this, posts, context);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
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

    @Override
    public void onGetPosts(PostResponseModel postResponseModel) {
        if (postResponseModel.getStatus() == 0) {
            initRecyclerView(postResponseModel.getPosts());
        }
    }

    private void iniStoryBar(int count) {
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
        if (position < storyPosition) {
            storiesProgressView.reverse();
        } else {
            storiesProgressView.skip();
        }
        storyPosition = position;
    }

    @Override
    public void onPostChanged(int position) {

    }


}
