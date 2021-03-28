package travelguideapp.ge.travelguide.ui.profile.posts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.callback.OnPostChooseCallback;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.parcelable.LoadPostParams;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;

import java.util.List;


public class UserPostsFragment extends Fragment implements UserPostListener {

    public static UserPostsFragment getInstance(OnPostChooseCallback callback) {
        UserPostsFragment userPostsFragment = new UserPostsFragment();
        userPostsFragment.callback = callback;
        return userPostsFragment;
    }

    private static final int sColumnWidth = 120; // assume cell width of 120dp

    private RecyclerView postsRecycler;

    private OnPostChooseCallback callback;
    private UserPostPresenter userPostPresenter;
    private List<PostResponse.Posts> posts;

    private UserPostAdapter postAdapter;

    private LoadPostParams.Source loadSource;
    private int customerUserId;

    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    private boolean isCustomer = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        postsRecycler = view.findViewById(R.id.customer_photo_recycler);
        postsRecycler.setLayoutManager(gridLayoutManager);
        postsRecycler.setHasFixedSize(true);

        userPostPresenter = new UserPostPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            if (getArguments() == null) {
                isCustomer = false;
                userPostPresenter.getUserPosts(GlobalPreferences.getAccessToken(postsRecycler.getContext()), new PostByUserRequest(GlobalPreferences.getUserId(postsRecycler.getContext()), 0));
            } else {
                this.loadSource = (LoadPostParams.Source) getArguments().getSerializable("request_type");
                if (loadSource == LoadPostParams.Source.CUSTOMER_POSTS) {
                    isCustomer = true;
                    this.customerUserId = getArguments().getInt("customer_user_id");
                    userPostPresenter.getUserPosts(GlobalPreferences.getAccessToken(postsRecycler.getContext()), new PostByUserRequest(customerUserId, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initRecycler(List<PostResponse.Posts> posts, boolean canLazyLoad) {
        try {
            postAdapter = new UserPostAdapter(this);
            postAdapter.setPosts(posts);
            postAdapter.setCanLazyLoad(canLazyLoad);
            postsRecycler.setAdapter(postAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
        try {
            if (postAdapter == null) {
                if (posts.size() < GlobalPreferences.getAppSettings(postsRecycler.getContext()).getPOST_PER_PAGE_SIZE()) {
                    initRecycler(posts, false);
                } else {
                    initRecycler(posts, true);
                }
                this.posts = posts;
            } else {
                this.posts.addAll(posts);
                postAdapter.setPosts(this.posts);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(getContext(), HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } catch (Exception b) {
                b.printStackTrace();
            }
        }

    }

    @Override
    public void onError(String message) {
        MyToaster.getToast(getContext(), message);
    }

    @Override
    public void onAuthenticationError(String message) {
        /*Supposedly TO-DO : Error when auth, redirect to sign page **/
    }

    @Override
    public void onConnectionError() {
        /*Supposedly TO-DO : No internet connection toast **/
    }

    @Override
    public void onLazyLoad(int postId) {
        if (userPostPresenter != null)
            if (isCustomer) {
                userPostPresenter.getUserPosts(GlobalPreferences.getAccessToken(postsRecycler.getContext()), new PostByUserRequest(customerUserId, postId));
            } else {
                userPostPresenter.getUserPosts(GlobalPreferences.getAccessToken(postsRecycler.getContext()), new PostByUserRequest(GlobalPreferences.getUserId(postsRecycler.getContext()), postId));
            }
    }

    @Override
    public void onPostChoose(int postId) {
        try {
            int position = getPositionById(postId);

            LoadPostParams postDataLoad = new LoadPostParams();

            Bundle bundle = new Bundle();
            postDataLoad.setPosts(posts);
            postDataLoad.setScrollPosition(postId);
            if (loadSource == LoadPostParams.Source.CUSTOMER_POSTS) {
                postDataLoad.setLoadSource(LoadPostParams.Source.CUSTOMER_POSTS);
                postDataLoad.setUserId(customerUserId);
            } else {
                postDataLoad.setLoadSource(LoadPostParams.Source.MY_POSTS);
                bundle.putBoolean("back_to_profile", true);
            }
            bundle.putParcelable(LoadPostParams.INTENT_KEY_LOAD, postDataLoad);
            callback.onPostChoose(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getPositionById(int postId) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getPost_id() == postId) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onStop() {
        if (userPostPresenter != null) {
            userPostPresenter = null;
        }
        super.onStop();
    }
}
