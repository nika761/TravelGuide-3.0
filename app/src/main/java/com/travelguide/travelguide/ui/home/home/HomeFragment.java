package com.travelguide.travelguide.ui.home.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.travelguide.travelguide.R;
import com.travelguide.travelguide.model.request.FavoritePostRequest;
import com.travelguide.travelguide.model.request.FollowRequest;
import com.travelguide.travelguide.model.request.SetPostFavoriteRequest;
import com.travelguide.travelguide.model.request.SetStoryLikeRequest;
import com.travelguide.travelguide.model.request.SharePostRequest;
import com.travelguide.travelguide.model.response.FollowResponse;
import com.travelguide.travelguide.model.response.SetPostFavoriteResponse;
import com.travelguide.travelguide.model.response.SetStoryLikeResponse;
import com.travelguide.travelguide.helper.HelperPref;
import com.travelguide.travelguide.model.request.PostRequest;
import com.travelguide.travelguide.model.response.PostResponse;
import com.travelguide.travelguide.model.response.SharePostResponse;
import com.travelguide.travelguide.ui.customerUser.CustomerProfileActivity;
import com.travelguide.travelguide.ui.home.HomePageActivity;
import com.travelguide.travelguide.ui.login.signIn.SignInActivity;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.travelguide.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class HomeFragment extends Fragment implements HomeFragmentListener {

    private static final int SHARING_REQUEST_CODE = 1;

    private HomeFragmentPresenter presenter;
    private PostAdapter postAdapter;

    private LottieAnimationView loader;
    private ConstraintLayout loaderContainer;
    private RecyclerView postRecycler;

    private int postId;
    private int oldPosition = -1;
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    private GetPostType getPostType;

    public enum GetPostType {
        FAVORITES, MY_POSTS, CUSTOMER_POSTS, FEED
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new HomeFragmentPresenter(this);

        loader = view.findViewById(R.id.loader_post);
        loaderContainer = view.findViewById(R.id.loader_constraint);

        postRecycler = view.findViewById(R.id.recycler_story);

        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(postRecycler);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            getPostType = (GetPostType) getArguments().getSerializable("PostShowType");
            if (getPostType != null) {
                switch (getPostType) {

                    case FAVORITES:
                        int scrollPosition = getArguments().getInt("postPosition");

                        List<PostResponse.Posts> favoritePosts = (List<PostResponse.Posts>) getArguments().getSerializable("favoritePosts");

                        initRecyclerView(favoritePosts, true, scrollPosition);

                        break;

                    case MY_POSTS:
                        int scroll = getArguments().getInt("postPosition");

                        List<PostResponse.Posts> myPosts = (List<PostResponse.Posts>) getArguments().getSerializable("myPosts");

                        initRecyclerView(myPosts, true, scroll);

                        break;

                    case CUSTOMER_POSTS:
                        break;

                    case FEED:
                        presenter.getPosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postRecycler.getContext()), new PostRequest(0));
                        break;
                }
            }
        }
    }

    private void initRecyclerView(List<PostResponse.Posts> posts, boolean scrollToPosition, int scrollPosition) {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        postRecycler.setLayoutManager(layoutManager);

        postAdapter = new PostAdapter(this);
        postAdapter.setPosts(posts);
        postRecycler.setAdapter(postAdapter);

        postRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                if (dy > 0) {
//                    visibleItemCount = layoutManager.getChildCount();
//                    totalItemCount = layoutManager.getItemCount();
//                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//
//                    Log.e("dasdasdaczxczxczxczx", visibleItemCount + " " + totalItemCount + " " + pastVisibleItems);
//
//                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                    }
//                }

                int firstVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (firstVisibleItem != oldPosition) {

                    PostAdapter.PostHolder postHolder = ((PostAdapter.PostHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItem));

                    if (postHolder != null) {
                        postHolder.storyRecycler.post(() -> postHolder.storyRecycler.smoothScrollToPosition(0));
                        postHolder.iniStory(firstVisibleItem);
                    }

                    PostAdapter.PostHolder oldHolder = ((PostAdapter.PostHolder) recyclerView.findViewHolderForAdapterPosition(oldPosition));

                    if (oldHolder != null) {
//                        oldHolder.delete(0);
                        oldHolder.storyView.removeAllViews();
                    }

                    oldPosition = firstVisibleItem;
                }
            }

        });

        if (scrollToPosition) {
            postRecycler.post(() -> postRecycler.scrollToPosition(scrollPosition));
        }

    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {

        if (postAdapter == null) {
            Log.e("lazyLoad", "adapter first time");

            initRecyclerView(posts, false, 0);

        } else {
            postAdapter.setPosts(posts);

            Log.e("lazyLoad", "adapter second time posts loaded");
        }

    }

    @Override
    public void onFollowChoose(int userId) {
        presenter.follow(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postRecycler.getContext()), new FollowRequest(userId));
    }

    @Override
    public void onFollowSuccess(FollowResponse followResponse) {
        switch (followResponse.getStatus()) {
            case 0:
            case 1:
                Toast.makeText(postRecycler.getContext(), followResponse.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onFavoriteChoose(int post_id, int position) {
        presenter.setPostFavorite(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postRecycler.getContext()), new SetPostFavoriteRequest(post_id));
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
        Log.e("shared", String.valueOf(sharePostResponse.getStatus()));
    }


    @Override
    public void onStoryLikeChoose(int postId, int storyId, int position) {
        presenter.setStoryLike(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postRecycler.getContext()), new SetStoryLikeRequest(storyId, postId));
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
    public void onCommentChoose(int storyId, int postId) {
        if (getContext() != null)
            ((HomePageActivity) getContext()).loadCommentFragment(storyId, postId);
    }

    @Override
    public void onUserChoose(int userId) {
        if (userId == HelperPref.getUserId(postRecycler.getContext())) {
            if (getContext() != null)
                ((HomePageActivity) getContext()).onProfileChoose();
        } else {
            Intent intent = new Intent(postRecycler.getContext(), CustomerProfileActivity.class);
            intent.putExtra("id", userId);
            postRecycler.getContext().startActivity(intent);
        }

    }

    @Override
    public void onLazyLoad(int fromPostId) {

        switch (getPostType) {

            case FAVORITES:
                presenter.getFavoritePosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postRecycler.getContext()), new FavoritePostRequest(postId));
                break;

            case MY_POSTS:
                break;

            case CUSTOMER_POSTS:
                break;

            case FEED:
                presenter.getPosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postRecycler.getContext()), new PostRequest(fromPostId));
                break;
        }

        Log.e("lazyLoad", "from post id " + fromPostId);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARING_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                presenter.setPostShare(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postRecycler.getContext()), new SharePostRequest(postId));
            }
        }
    }

    @Override
    public void onDestroy() {

        if (presenter != null) {
            presenter = null;
        }

        if (postAdapter != null) {
            postAdapter = null;
        }

        super.onDestroy();
    }


    @Override
    public void stopLoader() {
        loaderContainer.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onAuthError() {
        Intent intent = new Intent(postRecycler.getContext(), SignInActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(postRecycler.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
