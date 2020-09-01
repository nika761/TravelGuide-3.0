package com.example.travelguide.ui.home.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.CustomerPostRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
import com.example.travelguide.ui.home.adapter.recycler.PostRecyclerAdapter;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;

import java.util.List;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class PostActivity extends AppCompatActivity implements OnLoadFinishListener {
    private int oldPosition = -1;
    private int userId;
    private List<PostResponse.Posts> posts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.userId = getIntent().getIntExtra("user_id", 0);
    }

    private void initRecyclerView(List<PostResponse.Posts> posts) {

        RecyclerView recyclerView = findViewById(R.id.recycler_post_test);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(this, posts, this);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
    public void stopLoader() {

    }

    @Override
    public void startTimer(int postId) {

    }

    @Override
    public void resetTimer() {

    }

    @Override
    public void onStoryLikeChoose(int postId, int storyId) {

    }

    @Override
    public void onStoryLiked(SetStoryLikeResponse setStoryLikeResponse) {

    }

    @Override
    public void onStoryLikeError(String message) {

    }

    @Override
    public void onGetPosts(PostResponse postResponse) {

    }

    @Override
    public void onGetPostsError(String message) {

    }

    @Override
    public void onFollowChoose(int userId) {

    }

    @Override
    public void onFollowSuccess(FollowResponse followResponse) {

    }

    @Override
    public void onFollowError(String message) {

    }

    @Override
    public void onShareChoose(String postLink) {

    }

    @Override
    public void onFavoriteChoose(int post_id) {

    }

    @Override
    public void onFavoriteSuccess(SetPostFavoriteResponse setPostFavoriteResponse) {

    }

    @Override
    public void onFavoriteError(String message) {

    }
}
