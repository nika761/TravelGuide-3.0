package com.example.travelguide.ui.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.SetPostFavoriteRequest;
import com.example.travelguide.model.request.SetPostViewRequest;
import com.example.travelguide.model.request.SetStoryLikeRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
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

import static android.app.Activity.RESULT_OK;
import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class UserHomeFragment extends Fragment implements OnLoadFinishListener {
    private RecyclerView recyclerView;
    private HomePresenter homePresenter;
    private Context context;
    private LinearLayout storyContainer;
    private LottieAnimationView lottieAnimationView;
    private int oldPosition = -1;
    private CountDownTimer countDownTimer;
    private static final long POST_VIEW_TIMER = 3000;
    private static final long POST_VIEW_INTERVAL = 500;
    private static final int SHARING_REQUEST_CODE = 1;
    private long timeLeft = POST_VIEW_TIMER;
    private boolean timerRunning;

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
        homePresenter.getPosts(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken(context), postRequest);
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

                    PostRecyclerAdapter.PostHolder postHolder = ((PostRecyclerAdapter.PostHolder)
                            recyclerView.findViewHolderForAdapterPosition(firstVisibleItem));

                    if (postHolder != null) {
                        postHolder.recyclerView.post(() -> {
                            postHolder.recyclerView.smoothScrollToPosition(0);
                        });
                        postHolder.iniStory(firstVisibleItem);
                    }

                    PostRecyclerAdapter.PostHolder oldHolder = ((PostRecyclerAdapter.PostHolder)
                            recyclerView.findViewHolderForAdapterPosition(oldPosition));

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
    public void onGetPostsError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFollowChoose(int userId) {
        homePresenter.follow(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken(context), new FollowRequest(userId));
    }

    @Override
    public void onFollowSuccess(FollowResponse followResponse) {
        switch (followResponse.getStatus()) {
            case 0:
            case 1:
                Toast.makeText(context, followResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onFollowError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareChoose(String postLink) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, postLink);
        startActivityForResult(sharingIntent, SHARING_REQUEST_CODE);
//        startActivity(sharingIntent);
    }

    @Override
    public void onFavoriteChoose(int post_id) {
        homePresenter.setPostFavorite(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken(context), new SetPostFavoriteRequest(post_id));
    }

    @Override
    public void onFavoriteSuccess(SetPostFavoriteResponse setPostFavoriteResponse) {
        switch (setPostFavoriteResponse.getStatus()) {
            case 0:
            case 1:
                Toast.makeText(context, setPostFavoriteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onFavoriteError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARING_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.e("resultcoide", String.valueOf(resultCode));
            }
        }
    }

    private void startCountDownTimer(int postId) {
        if (countDownTimer != null)
            resetCountDownTimer();


        countDownTimer = new CountDownTimer(POST_VIEW_TIMER, POST_VIEW_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                homePresenter.setPostView(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken(context),
                        new SetPostViewRequest(postId));
                Log.e("mmmmnjjjj", "sent");

            }
        }.start();
        timerRunning = true;
    }

    private void resetCountDownTimer() {
        countDownTimer.cancel();
        countDownTimer = null;
        timerRunning = false;
    }

    @Override
    public void onDestroy() {
        if (homePresenter != null) {
            homePresenter = null;
        }

        if (countDownTimer != null) {
            countDownTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void stopLoader() {
        lottieAnimationView.setVisibility(View.GONE);
    }

    @Override
    public void startTimer(int postId) {
        startCountDownTimer(postId);
    }

    @Override
    public void resetTimer() {
        resetCountDownTimer();
    }

    @Override
    public void onStoryLikeChoose(int postId, int storyId) {
        homePresenter.setStoryLike(ACCESS_TOKEN_BEARER +
                HelperPref.getCurrentAccessToken(context), new SetStoryLikeRequest(storyId, postId));
    }

    @Override
    public void onStoryLiked(SetStoryLikeResponse setStoryLikeResponse) {
        switch (setStoryLikeResponse.getStatus()) {
            case 0:
            case 1:
                Toast.makeText(context, setStoryLikeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onStoryLikeError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
