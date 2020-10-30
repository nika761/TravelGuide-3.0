package com.example.travelguide.ui.home.home;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.SetPostFavoriteRequest;
import com.example.travelguide.model.request.SetStoryLikeRequest;
import com.example.travelguide.model.request.SharePostRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.PostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.SharePostResponse;
import com.example.travelguide.ui.home.HomePageActivity;
import com.example.travelguide.ui.login.signIn.SignInActivity;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class HomeFragment extends Fragment implements HomeFragmentListener {

    private HomeFragmentPresenter homeFragmentPresenter;
    private List<PostResponse.Posts> posts;
    private PostAdapter postRecyclerAdapter;
    private CountDownTimer countDownTimer;

    private LottieAnimationView lottieAnimationView;
    private ConstraintLayout loaderConstraint;
    private LinearLayout storyContainer;
    private RecyclerView recyclerView;
    private Context context;

    private static final long POST_VIEW_TIMER = 3000;
    private static final long POST_VIEW_INTERVAL = 500;
    private static final int SHARING_REQUEST_CODE = 1;

    private boolean timerRunning;
    private boolean loading = true;
    private long timeLeft = POST_VIEW_TIMER;
    private int postId;
    private int oldPosition = -1;
    private int storyPosition;
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;


    public HomeFragment() {

    }

    public HomeFragment(List<PostResponse.Posts> posts) {
        this.posts = posts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lottieAnimationView = view.findViewById(R.id.loader_post);
        loaderConstraint = view.findViewById(R.id.loader_constraint);
        recyclerView = view.findViewById(R.id.recycler_story);

//        View decorView = window.getDecorView();
//        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (posts != null) {
            initRecyclerView(posts);
        } else {
            homeFragmentPresenter = new HomeFragmentPresenter(this);
            homeFragmentPresenter.getPosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context),
                    new PostRequest(0));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initRecyclerView(List<PostResponse.Posts> posts) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        postRecyclerAdapter = new PostAdapter(this, context, posts);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(postRecyclerAdapter);

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

                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    Log.e("dasdasdaczxczxczxczx", visibleItemCount + " " + totalItemCount + " " + pastVisibleItems);

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = false;
                        homeFragmentPresenter.getLazyPosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context),
                                new PostRequest(postRecyclerAdapter.getPostId()));

                        Log.e("postsdsdsd", String.valueOf(postRecyclerAdapter.getPostId()));

                    }
                }

                int firstVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (firstVisibleItem != oldPosition) {

                    PostAdapter.PostHolder postHolder = ((PostAdapter.PostHolder)
                            recyclerView.findViewHolderForAdapterPosition(firstVisibleItem));

                    if (postHolder != null) {
                        postHolder.recyclerView.post(() -> postHolder.recyclerView.smoothScrollToPosition(0));
                        postHolder.iniStory(firstVisibleItem);
                    }

                    PostAdapter.PostHolder oldHolder = ((PostAdapter.PostHolder) recyclerView.findViewHolderForAdapterPosition(oldPosition));

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
    public void onGetLazyPosts(PostResponse postResponse) {
        if (postResponse.getStatus() == 0)
            postRecyclerAdapter.setPosts(postResponse.getPosts());
    }

    @Override
    public void onGetPostsError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFollowChoose(int userId) {
        homeFragmentPresenter.follow(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FollowRequest(userId));
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
    public void onFavoriteChoose(int post_id, int position) {
        this.storyPosition = position;
        homeFragmentPresenter.setPostFavorite(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new SetPostFavoriteRequest(post_id));
    }

    @Override
    public void onFavoriteSuccess(SetPostFavoriteResponse setPostFavoriteResponse) {
        switch (setPostFavoriteResponse.getStatus()) {
            case 0:
//                Toast.makeText(context, setPostFavoriteResponse.getMessage(), Toast.LENGTH_SHORT).show();
            case 1:
//                postRecyclerAdapter.setStoryFavorite(1, storyPosition, setPostFavoriteResponse.getCount());
//                Toast.makeText(context, setPostFavoriteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onFavoriteError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareChoose(String postLink, int post_id) {
        this.postId = post_id;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, postLink);
        startActivityForResult(sharingIntent, SHARING_REQUEST_CODE);
    }

    @Override
    public void onShareSuccess(SharePostResponse sharePostResponse) {
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentChoose(int storyId, int postId) {
        ((HomePageActivity) context).loadCommentFragment(storyId, postId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARING_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                homeFragmentPresenter.setPostShare(ACCESS_TOKEN_BEARER +
                        HelperPref.getAccessToken(context), new SharePostRequest(postId));
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
//                homeFragmentPresenter.setPostView(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context),
//                        new SetPostViewRequest(postId));
//                Log.e("mmmmnjjjj", "sent");

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

        if (homeFragmentPresenter != null) {
            homeFragmentPresenter = null;
        }

        if (countDownTimer != null) {
            countDownTimer = null;
        }

        super.onDestroy();
    }

    @Override
    public void stopLoader() {
        loaderConstraint.setVisibility(View.GONE);
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
    public void onLoginError() {
        Intent intent = new Intent(context, SignInActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onStoryLikeChoose(int postId, int storyId, int position) {
        this.storyPosition = position;
        homeFragmentPresenter.setStoryLike(ACCESS_TOKEN_BEARER +
                HelperPref.getAccessToken(context), new SetStoryLikeRequest(storyId, postId));
    }

    @Override
    public void onStoryLiked(SetStoryLikeResponse setStoryLikeResponse) {
        switch (setStoryLikeResponse.getStatus()) {
            case 0:
//                postRecyclerAdapter.setStoryLike(0, storyPosition, setStoryLikeResponse.getStory().getStory_likes());
//                Toast.makeText(context, setStoryLikeResponse.getMessage(), Toast.LENGTH_SHORT).show();
            case 1:
//                postRecyclerAdapter.setStoryLike(1, storyPosition, setStoryLikeResponse.getStory().getStory_likes());
//                Toast.makeText(context, setStoryLikeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onStoryLikeError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
