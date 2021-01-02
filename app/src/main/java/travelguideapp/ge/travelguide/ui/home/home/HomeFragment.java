package travelguideapp.ge.travelguide.ui.home.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.GetPostsFrom;
import travelguideapp.ge.travelguide.enums.LoadWebViewBy;
import travelguideapp.ge.travelguide.enums.SearchPostBy;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.custom.CustomTimer;
import travelguideapp.ge.travelguide.helper.custom.customPost.CustomPostAdapter;
import travelguideapp.ge.travelguide.helper.custom.customPost.CustomPostRecycler;
import travelguideapp.ge.travelguide.helper.custom.CustomProgressBar;
import travelguideapp.ge.travelguide.model.PostView;
import travelguideapp.ge.travelguide.model.request.DeleteStoryRequest;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.request.SetPostFavoriteRequest;
import travelguideapp.ge.travelguide.model.request.SetPostViewRequest;
import travelguideapp.ge.travelguide.model.request.SetStoryLikeRequest;
import travelguideapp.ge.travelguide.model.request.SharePostRequest;
import travelguideapp.ge.travelguide.model.response.DeleteStoryResponse;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.SetPostFavoriteResponse;
import travelguideapp.ge.travelguide.model.response.SetStoryLikeResponse;
import travelguideapp.ge.travelguide.ui.home.comments.CommentFragment;
import travelguideapp.ge.travelguide.ui.searchPost.SearchPostActivity;
import travelguideapp.ge.travelguide.utility.BaseApplication;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.PostRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.model.response.SharePostResponse;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.ui.home.customerUser.CustomerProfileActivity;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;

import java.util.List;

import static travelguideapp.ge.travelguide.utility.BaseApplication.SHARING_INTENT_REQUEST_CODE;

public class HomeFragment extends Fragment implements HomeFragmentListener {

    public static HomeFragment getInstance(HomeFragment.LoadCommentFragmentListener commentFragmentCallback) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.commentFragmentCallback = commentFragmentCallback;
        return homeFragment;
    }

    private HomeFragment.LoadCommentFragmentListener commentFragmentCallback;

    private HomeFragmentPresenter presenter;
    private PostAdapter postAdapter;

    private LottieAnimationView loader;
    private ConstraintLayout loaderContainer;
    private RecyclerView postRecycler;
    private CustomProgressBar customProgressBar;

    private int customerUserId;

    private GetPostsFrom getPostsFrom;
    private CustomPostRecycler customPostRecycler;
    private CustomPostAdapter customPostAdapter;

    private List<PostResponse.Posts> posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.setStatusBarColor(getResources().getColor(R.color.black, null));
            setSystemBarTheme(getActivity(), true);
//            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        presenter = new HomeFragmentPresenter(this);

        loader = view.findViewById(R.id.loader_post);
        loaderContainer = view.findViewById(R.id.loader_constraint);

        customProgressBar = view.findViewById(R.id.post_progress_container);

        postRecycler = view.findViewById(R.id.recycler_story);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(postRecycler);

        customPostRecycler = view.findViewById(R.id.testing_recycler);
        customPostRecycler.setLayoutManager(new LinearLayoutManager(customPostRecycler.getContext()));
        customPostRecycler.setHomeFragmentListener(this);
//        customPostRecycler.setCustomProgressBar(customProgressBar);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(customPostRecycler);

        return view;
    }

    private void setSystemBarTheme(final Activity activity, final boolean pIsDark) {
        try {
            final int lFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
            activity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            try {
                getPostsFrom = (GetPostsFrom) getArguments().getSerializable("PostShowType");
                int scrollPosition = getArguments().getInt("postPosition");
                if (getPostsFrom != null) {
                    switch (getPostsFrom) {
                        case FAVORITES:
                            List<PostResponse.Posts> favoritePosts = (List<PostResponse.Posts>) getArguments().getSerializable("favoritePosts");
                            initRecyclerView(favoritePosts, true, scrollPosition);
                            break;

                        case MY_POSTS:
                            List<PostResponse.Posts> myPosts = (List<PostResponse.Posts>) getArguments().getSerializable("my_posts");
                            initRecyclerView(myPosts, true, scrollPosition);
                            break;

                        case CUSTOMER_POSTS:
                            this.customerUserId = getArguments().getInt("customer_user_id");
                            List<PostResponse.Posts> customerPosts = (List<PostResponse.Posts>) getArguments().getSerializable("customer_posts");
                            initRecyclerView(customerPosts, true, scrollPosition);
                            break;

                        case FEED:
                            loaderContainer.setVisibility(View.VISIBLE);
                            presenter.getPosts(GlobalPreferences.getAccessToken(postRecycler.getContext()), new PostRequest(0));
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void initRecyclerView(List<PostResponse.Posts> posts, boolean scrollToPosition, int scrollPosition) {
        customPostRecycler.setPosts(posts);
        customPostAdapter = new CustomPostAdapter(this);
        customPostAdapter.setPosts(posts);
        customPostRecycler.setAdapter(customPostAdapter);
        try {
            if (scrollToPosition) {
                if (scrollPosition == 0)
                    customPostRecycler.post(() -> customPostRecycler.smoothScrollBy(0, 1));
                else {
                    customPostRecycler.post(() -> customPostRecycler.smoothScrollToPosition(scrollPosition));
                }
            } else {
                customPostRecycler.post(() -> customPostRecycler.smoothScrollBy(0, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        postRecycler.setLayoutManager(layoutManager);
//
//        postAdapter = new CustomPostAdapter(this);
//        postAdapter.setPosts(posts);
//        postRecycler.setAdapter(postAdapter);
//
//        if (scrollToPosition) {
//            postRecycler.post(() -> postRecycler.scrollToPosition(scrollPosition));
//        }
//
//        postRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
////                if (dy > 0) {
////                    visibleItemCount = layoutManager.getChildCount();
////                    totalItemCount = layoutManager.getItemCount();
////                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
////
////                    Log.e("dasdasdaczxczxczxczx", visibleItemCount + " " + totalItemCount + " " + pastVisibleItems);
////
////                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
////                    }
////                }
//
//                int firstVisibleItem = layoutManager.findLastVisibleItemPosition();
//
//                if (firstVisibleItem != oldPosition) {
//
//                    CustomPostAdapter.CustomPostHolder postHolder = ((CustomPostAdapter.CustomPostHolder) recyclerView.findViewHolderForAdapterPosition(firstVisibleItem));
//
//                    if (postHolder != null) {
//                        postHolder.storyRecycler.post(() -> postHolder.storyRecycler.smoothScrollToPosition(0));
//                        postHolder.iniStory(firstVisibleItem);
//                    }
//
//                    CustomPostAdapter.CustomPostHolder oldHolder = ((CustomPostAdapter.CustomPostHolder) recyclerView.findViewHolderForAdapterPosition(oldPosition));
//
//                    if (oldHolder != null) {
////                        oldHolder.delete(0);
//                        oldHolder.storyView.removeAllViews();
//                    }
//
//                    oldPosition = firstVisibleItem;
//                }
//            }
//
////            @Override
////            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
////                super.onScrollStateChanged(recyclerView, newState);
////
////                switch (newState) {
////
////                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
////
////                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
////                        break;
////
////                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
////                        if (oldHolder != null) {
////                            oldHolder.delete(0);
////                            oldHolder.storyView.removeAllViews();
////                        }
////                        break;
////
////                }
////            }
//        });

    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
//
//        if (postAdapter == null) {
//            initRecyclerView(posts, false, 0);
//        } else {
//            postAdapter.setPosts(posts);
//        }

//        if (customPostAdapter == null) {
//            initRecyclerView(posts, false, 0);
//        } else {
//            customPostAdapter.setPosts(posts);
//            customPostRecycler.setPosts(posts);
//        }
        try {
            if (customPostAdapter == null) {
                initRecyclerView(posts, false, 0);
                this.posts = posts;
            } else {
                this.posts.addAll(posts);
                customPostAdapter.setPosts(this.posts);
                customPostRecycler.setPosts(this.posts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChoose(int postId, SearchPostBy searchPostBy) {
        Intent postHashtagIntent = new Intent(customPostRecycler.getContext(), SearchPostActivity.class);
        postHashtagIntent.putExtra("search_type", searchPostBy);
        postHashtagIntent.putExtra("search_post_id", postId);
        customPostRecycler.getContext().startActivity(postHashtagIntent);
    }

    @Override
    public void onFollowChoose(int userId) {
        presenter.follow(GlobalPreferences.getAccessToken(postRecycler.getContext()), new FollowRequest(userId));
    }

    @Override
    public void onFollowAdded() {
        /*Supposedly TO-DO : update recycler view and adapter by this response : Now updating by local changes, for each item  **/
    }

    @Override
    public void onFollowRemoved() {
        /*Supposedly TO-DO : update recycler view and adapter by this response : Now updating by local changes, for each item  **/
    }

    @Override
    public void onFavoriteChoose(int post_id) {
        presenter.setPostFavorite(GlobalPreferences.getAccessToken(postRecycler.getContext()), new SetPostFavoriteRequest(post_id));
    }

    @Override
    public void onFavoriteAdded() {
        /*Supposedly TO-DO : update recycler view and adapter by this response : Now updating by local changes, for each item  **/
    }

    @Override
    public void onFavoriteRemoved() {
        /*Supposedly TO-DO : update recycler view and adapter by this response : Now updating by local changes, for each item  **/
    }

    @Override
    public void onGoChoose(String url) {
        HelperUI.startWebActivity(postRecycler.getContext(), LoadWebViewBy.GO, url);
    }

    @Override
    public void onShareChoose(String postLink, int post_id) {
        try {
            presenter.setPostShare(GlobalPreferences.getAccessToken(postRecycler.getContext()), new SharePostRequest(post_id));

            BaseApplication.shareContent((Activity) postRecycler.getContext(), postLink);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShared(SharePostResponse sharePostResponse) {
        /*Supposedly TO-DO : update ui by this response **/
    }


    @Override
    public void onStoryLikeChoose(int postId, int storyId) {
        presenter.setStoryLike(GlobalPreferences.getAccessToken(postRecycler.getContext()), new SetStoryLikeRequest(storyId, postId));
    }

    @Override
    public void onStoryLiked() {
        /*Supposedly TO-DO : update recycler view and adapter by this response : Now updating by local changes, for each item  **/
    }

    @Override
    public void onStoryUnLiked() {
        /*Supposedly TO-DO : update recycler view and adapter by this response : Now updating by local changes, for each item  **/
    }

    @Override
    public void onCommentChoose(int storyId, int postId) {
        try {
            commentFragmentCallback.onLoadCommentFragment(storyId, postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            if (getContext() instanceof HomePageActivity) {
//                ((HomePageActivity) getContext()).loadCommentFragment(storyId, postId);
//            } else if (getContext() instanceof CustomerProfileActivity) {
//                ((CustomerProfileActivity) getContext()).loadCommentFragment(storyId, postId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUserChoose(int userId) {
        if (userId == GlobalPreferences.getUserId(postRecycler.getContext())) {
            try {
                ((HomePageActivity) getContext()).onProfileChoose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Intent intent = new Intent(postRecycler.getContext(), CustomerProfileActivity.class);
                intent.putExtra("id", userId);
                postRecycler.getContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onChooseDeleteStory(int storyId, int postId, int position) {
        DialogManager.getAskingDialog(postRecycler.getContext(), getString(R.string.delete_story), () -> presenter.deleteStory(GlobalPreferences.getAccessToken(postRecycler.getContext()), new DeleteStoryRequest(postId, storyId)));
    }

    @Override
    public void onStoryDeleted(DeleteStoryResponse deleteStoryResponse) {
        try {
            Intent intent = new Intent(getContext(), HomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLazyLoad(int fromPostId) {

        switch (getPostsFrom) {

            case FAVORITES:
                presenter.getFavoritePosts(GlobalPreferences.getAccessToken(postRecycler.getContext()), new FavoritePostRequest(fromPostId));
                break;

            case MY_POSTS:
                presenter.getUserPosts(GlobalPreferences.getAccessToken(postRecycler.getContext()), new PostByUserRequest(GlobalPreferences.getUserId(postRecycler.getContext()), fromPostId));
                break;

            case CUSTOMER_POSTS:
                presenter.getUserPosts(GlobalPreferences.getAccessToken(postRecycler.getContext()), new PostByUserRequest(customerUserId, fromPostId));
                break;

            case FEED:
                presenter.getPosts(GlobalPreferences.getAccessToken(postRecycler.getContext()), new PostRequest(fromPostId));
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARING_INTENT_REQUEST_CODE) {
            /*Supposedly TO-DO : handle sharing request by result **/
        }
    }

    @Override
    public void onStop() {
        try {
            List<PostView> views = CustomTimer.getPostViews();
            if (views.size() != 0)
                presenter.setPostViews(GlobalPreferences.getAccessToken(getContext()), new SetPostViewRequest(views));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (customPostRecycler != null)
            customPostRecycler.releasePlayer();

        if (presenter != null)
            presenter = null;

        super.onStop();
    }

    @Override
    public void stopLoader() {
        try {
            loaderContainer.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAuthError(String message) {
        try {
            MyToaster.getErrorToaster(getContext(), message);
            Intent intent = new Intent(getContext(), SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String message) {
        try {
            stopLoader();
            MyToaster.getErrorToaster(postRecycler.getContext(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface LoadCommentFragmentListener {
        void onLoadCommentFragment(int postId, int storyId);
    }

}
