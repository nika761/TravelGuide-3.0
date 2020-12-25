package travelguideapp.ge.travelguide.ui.home.profile.posts;

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
import travelguideapp.ge.travelguide.enums.GetPostsFrom;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.ui.home.profile.ProfileFragment;

import java.io.Serializable;
import java.util.List;

public class UserPostsFragment extends Fragment implements UserPostListener {

    private static final int sColumnWidth = 120; // assume cell width of 120dp

    private RecyclerView postsRecycler;

    private ProfileFragment.OnPostChooseListener listener;
    private UserPostPresenter userPostPresenter;
    private List<PostResponse.Posts> posts;

    private UserPostAdapter postAdapter;

    private GetPostsFrom getPostsFrom;
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

        try {
            this.listener = (ProfileFragment.OnPostChooseListener) postsRecycler.getContext();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

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
                this.getPostsFrom = (GetPostsFrom) getArguments().getSerializable("request_type");
                if (getPostsFrom == GetPostsFrom.CUSTOMER_POSTS) {
                    isCustomer = true;
                    this.customerUserId = getArguments().getInt("customer_user_id");
                    userPostPresenter.getUserPosts(GlobalPreferences.getAccessToken(postsRecycler.getContext()), new PostByUserRequest(customerUserId, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initRecycler(List<PostResponse.Posts> posts) {
        try {
            postAdapter = new UserPostAdapter(this);

//            int itemWidth = HelperMedia.getScreenWidth(getActivity());
//            if (itemWidth != 0)
//                postAdapter.setItemWidth(itemWidth);

            postAdapter.setPosts(posts);
            postsRecycler.setAdapter(postAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        postsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    visibleItemCount = gridLayoutManager.getChildCount();
//                    totalItemCount = gridLayoutManager.getItemCount();
//                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
//
//                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                        if (isCustomer) {
//
//                        }
//                        Log.e("dasdasdaczxczxczxczx", "now");
//                    }
//
//                }
//            }
//        });

    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
        try {
            if (postAdapter == null) {
                initRecycler(posts);
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
    public void onGetPostsError(String message) {
        MyToaster.getErrorToaster(getContext(), message);
    }

    @Override
    public void onLazyLoad(int postId) {
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

            Bundle data = new Bundle();
            data.putInt("postPosition", position);
            if (getPostsFrom == GetPostsFrom.CUSTOMER_POSTS) {
                data.putSerializable("PostShowType", GetPostsFrom.CUSTOMER_POSTS);
                data.putInt("customer_user_id", customerUserId);
                data.putSerializable("customer_posts", (Serializable) posts);
            } else {
                data.putSerializable("PostShowType", GetPostsFrom.MY_POSTS);
                data.putSerializable("my_posts", (Serializable) posts);
            }
            listener.onPostChoose(data);
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
