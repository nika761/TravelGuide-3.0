package com.travelguide.travelguide.ui.home.profile.posts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travelguide.travelguide.R;
import com.travelguide.travelguide.helper.HelperPref;
import com.travelguide.travelguide.model.request.PostByUserRequest;
import com.travelguide.travelguide.model.response.PostResponse;
import com.travelguide.travelguide.ui.home.home.HomeFragment;
import com.travelguide.travelguide.ui.home.profile.ProfileFragment;

import java.io.Serializable;
import java.util.List;

import static com.travelguide.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class UserPostsFragment extends Fragment implements UserPostListener {


    private RecyclerView recyclerView;

    private ProfileFragment.OnPostChooseListener listener;
    private UserPostPresenter userPostPresenter;
    private List<PostResponse.Posts> posts;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);

        userPostPresenter = new UserPostPresenter(this);
        recyclerView = view.findViewById(R.id.customer_photo_recycler);

        this.listener = (ProfileFragment.OnPostChooseListener) recyclerView.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPostPresenter.getUserPosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(recyclerView.getContext()),
                new PostByUserRequest(HelperPref.getUserId(recyclerView.getContext())));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(recyclerView.getContext(), animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    @Override
    public void onGetPosts(PostResponse postResponse) {

        this.posts = postResponse.getPosts();

        UserPostAdapter adapter = new UserPostAdapter(postResponse.getPosts(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

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
        data.putSerializable("PostShowType", HomeFragment.GetPostType.MY_POSTS);
        data.putSerializable("myPosts", (Serializable) posts);

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
