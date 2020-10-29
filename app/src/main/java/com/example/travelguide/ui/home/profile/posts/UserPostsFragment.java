package com.example.travelguide.ui.home.profile.posts;

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

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.PostByUserRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.ui.gallery.GalleryFragment;
import com.example.travelguide.ui.home.profile.ProfileFragment;

import java.util.List;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class UserPostsFragment extends Fragment implements UserPostListener {

    private TextView firstPhotoContent;
    private UserPostPresenter userPostPresenter;
    private List<PostResponse.Posts> posts;
    private RecyclerView recyclerView;
    private Context context;
    private int userId;
    private ProfileFragment.OnPostChooseListener onPostChooseListener;

    public UserPostsFragment() {
    }

    public UserPostsFragment(int userId, Context context) {
        this.userId = userId;
        this.context = context;
        this.onPostChooseListener = (ProfileFragment.OnPostChooseListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);

        userPostPresenter = new UserPostPresenter(this);
        recyclerView = view.findViewById(R.id.customer_photo_recycler);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPostPresenter.getUserPosts(ACCESS_TOKEN_BEARER +
                HelperPref.getAccessToken(recyclerView.getContext()), new PostByUserRequest(userId));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
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
    public void onPostChoose() {
        onPostChooseListener.onPostChoose(posts);
    }

    @Override
    public void onDestroy() {
        if (userPostPresenter != null) {
            userPostPresenter = null;
        }
        super.onDestroy();
    }
}
