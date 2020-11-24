package com.travel.guide.ui.home.profile.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.enums.GetPostsFrom;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.request.PostByUserRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.ui.home.profile.ProfileFragment;

import java.io.Serializable;
import java.util.List;

import static com.travel.guide.enums.GetPostsFrom.CUSTOMER_POSTS;
import static com.travel.guide.enums.GetPostsFrom.MY_POSTS;
import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class UserPostsFragment extends Fragment implements UserPostListener {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);
        postsRecycler = view.findViewById(R.id.customer_photo_recycler);


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
        if (getArguments() == null) {
            userPostPresenter.getUserPosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postsRecycler.getContext()), new PostByUserRequest(HelperPref.getUserId(postsRecycler.getContext()), 0));
        } else {
            this.getPostsFrom = (GetPostsFrom) getArguments().getSerializable("request_type");

            if (getPostsFrom == GetPostsFrom.CUSTOMER_POSTS) {
                this.customerUserId = getArguments().getInt("customer_user_id");
                userPostPresenter.getUserPosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(postsRecycler.getContext()), new PostByUserRequest(customerUserId, 0));
            }
        }
    }


    private void initRecycler(List<PostResponse.Posts> posts) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        postsRecycler.setLayoutManager(gridLayoutManager);
        postsRecycler.setHasFixedSize(true);

        postAdapter = new UserPostAdapter(this);
        postAdapter.setPosts(posts);
        postsRecycler.setAdapter(postAdapter);

        postsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    Log.e("dasdasdaczxczxczxczx", visibleItemCount + " " + totalItemCount + " " + pastVisibleItems);

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    }

                }
            }
        });

    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
        this.posts = posts;

        if (postAdapter == null)
            initRecycler(posts);
        else
            postAdapter.setPosts(posts);
    }

    @Override
    public void onGetPostsError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostChoose(int postId) {

        int position = getPositionById(postId);

        Bundle data = new Bundle();
        data.putInt("postPosition", position);
        if (getPostsFrom == CUSTOMER_POSTS) {
            data.putSerializable("PostShowType", CUSTOMER_POSTS);
            data.putInt("customer_user_id", customerUserId);
            data.putSerializable("customer_posts", (Serializable) posts);
        } else {
            data.putSerializable("PostShowType", MY_POSTS);
            data.putSerializable("my_posts", (Serializable) posts);
        }

        listener.onPostChoose(data);
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
    public void onDestroy() {
        if (userPostPresenter != null) {
            userPostPresenter = null;
        }
        super.onDestroy();
    }
}
