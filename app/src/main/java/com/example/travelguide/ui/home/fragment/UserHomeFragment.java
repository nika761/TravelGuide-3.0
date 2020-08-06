package com.example.travelguide.ui.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.ui.home.adapter.recycler.PostRecyclerAdapter;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.ui.home.interfaces.IHomeFragment;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;
import com.example.travelguide.model.request.PostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.ui.home.presenter.HomePresenter;

import java.util.List;
import java.util.Objects;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class UserHomeFragment extends Fragment implements IHomeFragment, OnLoadFinishListener {
    private RecyclerView recyclerView;
    private HomePresenter homePresenter;
    private Context context;
    private LinearLayout storyContainer;
    private StoriesProgressView storiesProgressView;
    private LottieAnimationView lottieAnimationView;
    private int oldPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lottieAnimationView = view.findViewById(R.id.loader_post);
        recyclerView = view.findViewById(R.id.recycler_story);

//        View decorView = window.getDecorView();
//        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresenter = new HomePresenter(this);
        PostRequest postRequest = new PostRequest(0);
        homePresenter.getPosts("Bearer" + " " + HelperPref.getCurrentAccessToken(context), postRequest);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initRecyclerView(List<PostResponse.Posts> posts) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(this, posts, context);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (firstVisibleItem != oldPosition) {
                    PostRecyclerAdapter.PostHolder postHolder = ((PostRecyclerAdapter.PostHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItem));

                    if (postHolder != null) {
                        postHolder.recyclerView.post(() -> {
                            postHolder.recyclerView.smoothScrollToPosition(0);
                        });
                        postHolder.iniStory(firstVisibleItem);
                    }
                    PostRecyclerAdapter.PostHolder oldHolder = ((PostRecyclerAdapter.PostHolder) recyclerView.findViewHolderForAdapterPosition(oldPosition));
                    if (oldHolder != null) {
                        oldHolder.storyView.removeAllViews();
                    }
                    oldPosition = firstVisibleItem;
                }

            }
        });

    }

    @Override
    public void onGetPosts(PostResponse postResponse) {
        if (postResponse.getStatus() == 0) {
            initRecyclerView(postResponse.getPosts());
        }
    }

    @Override
    public void onDestroy() {
        if (homePresenter != null) {
            homePresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void stopLoader() {
        lottieAnimationView.setVisibility(View.GONE);
    }

}
