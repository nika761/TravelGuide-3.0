package com.travel.guide.ui.home.profile.favorites;

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

import com.travel.guide.R;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.request.FavoritePostRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.ui.home.home.HomeFragment;
import com.travel.guide.ui.home.profile.ProfileFragment;

import java.io.Serializable;
import java.util.List;

import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FavoritePostFragment extends Fragment implements FavoritePostListener {

    private TextView tabMain, tabVideos, tabPhotos, tabAll;
    private View contentList;
    private Context context;
    private RecyclerView recyclerView;

    private boolean visible = true;
    private List<PostResponse.Posts> posts;
    private FavoritePostPresenter favoritePostPresenter;
    private ProfileFragment.OnPostChooseListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_p_liked, container, false);

        favoritePostPresenter = new FavoritePostPresenter(this);

        listener = (ProfileFragment.OnPostChooseListener) context;

        contentList = view.findViewById(R.id.tab_liked_main_cont);

        recyclerView = view.findViewById(R.id.favorite_post_recycler);

        tabVideos = view.findViewById(R.id.tab_liked_video);
        tabVideos.setOnClickListener(this::onTabItemClick);

        tabPhotos = view.findViewById(R.id.tab_liked_photo);
        tabPhotos.setOnClickListener(this::onTabItemClick);

        tabMain = view.findViewById(R.id.tab_liked_main);
        tabMain.setOnClickListener(this::onTabItemClick);

        tabAll = view.findViewById(R.id.tab_liked_all);
        tabAll.setOnClickListener(this::onTabItemClick);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritePostPresenter.getFavoritePosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FavoritePostRequest(0));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private void setTabTextColor(TextView textView, int color) {
        textView.setTextColor(getResources().getColor(color));
    }

    private void onTabItemClick(View v) {
        switch (v.getId()) {

            case R.id.tab_liked_main:
                if (visible) {
                    contentList.setVisibility(View.VISIBLE);
                    visible = false;
                } else {
                    contentList.setVisibility(View.GONE);
                    visible = true;
                }
                break;

            case R.id.tab_liked_video:
                tabMain.setText("Videos");
                setTabTextColor(tabVideos, R.color.yellowTextView);
                setTabTextColor(tabPhotos, R.color.black);
                setTabTextColor(tabAll, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;

                break;

            case R.id.tab_liked_photo:
                tabMain.setText("Photos");
                setTabTextColor(tabPhotos, R.color.yellowTextView);
                setTabTextColor(tabVideos, R.color.black);
                setTabTextColor(tabAll, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;

                break;

            case R.id.tab_liked_all:
                tabMain.setText("All");
                setTabTextColor(tabAll, R.color.yellowTextView);
                setTabTextColor(tabPhotos, R.color.black);
                setTabTextColor(tabVideos, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;

                break;
        }
    }

    @Override
    public void onGetPosts(PostResponse postResponse) {
        this.posts = postResponse.getPosts();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        FavoritePostAdapter adapter = new FavoritePostAdapter(postResponse.getPosts(), this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onGetPostsError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostChoose(int postId) {
        int position = 0;

        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getPost_id() == postId) {
                position = i;
            }
        }

        Bundle data = new Bundle();
        data.putInt("postPosition", position);
        data.putSerializable("PostShowType", HomeFragment.GetPostType.FAVORITES);
        data.putSerializable("favoritePosts", (Serializable) posts);

        listener.onPostChoose(data);
    }

    @Override
    public void onDestroy() {
        if (favoritePostPresenter != null) {
            favoritePostPresenter = null;
        }
        super.onDestroy();
    }
}
