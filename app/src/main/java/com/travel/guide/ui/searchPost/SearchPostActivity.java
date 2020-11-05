package com.travel.guide.ui.searchPost;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.guide.R;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.model.request.PostByHashtagRequest;
import com.travel.guide.model.request.PostByLocationRequest;
import com.travel.guide.model.response.PostResponse;

import static com.travel.guide.helper.HelperUI.UI_HASHTAG;
import static com.travel.guide.helper.HelperUI.UI_LOCATION;
import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class SearchPostActivity extends AppCompatActivity implements SearchPostListener {

    private SearchPostPresenter searchPostPresenter;
    private TextView head;

    private String type;
    private String hashtag;
    private int postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_locatiom);

        this.postId = getIntent().getIntExtra("search_post_id", 0);
        this.hashtag = getIntent().getStringExtra("search_hashtag");
        this.type = getIntent().getStringExtra("search_type");

        initUI();

        switch (type) {

            case UI_LOCATION:
                searchPostPresenter.getPostsByLocation(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(this), new PostByLocationRequest(postId));
                break;
            case UI_HASHTAG:
                searchPostPresenter.getPostsByHashtag(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(this), new PostByHashtagRequest(hashtag));
                break;

        }
    }

    private void initUI() {
        head = findViewById(R.id.posts_by_location);

        ImageButton backBtn = findViewById(R.id.posts_by_location_back_btn);
        backBtn.setOnClickListener(v -> finish());

        searchPostPresenter = new SearchPostPresenter(this);
    }

    @Override
    public void onGetPosts(PostResponse postResponse) {
        switch (type) {

            case UI_LOCATION:
                if (postResponse.getPosts() != null)
                    if (postResponse.getPosts().size() != 0)
                        head.setText(postResponse.getPosts().get(0).getPost_locations().get(0).getAddress());
                break;

            case UI_HASHTAG:
                head.setText(hashtag);
                break;

        }

        SearchPostAdapter adapter = new SearchPostAdapter(postResponse.getPosts());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        RecyclerView recyclerView = findViewById(R.id.posts_by_location_recycler);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetPostError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}