package com.example.travelguide.ui.home.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.PostByLocationRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.ui.home.adapter.recycler.PostByLocationAdapter;
import com.example.travelguide.ui.home.interfaces.IPostByLocationActivity;
import com.example.travelguide.ui.home.presenter.PostsByLocationPresenter;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class PostsByLocationActivity extends AppCompatActivity implements IPostByLocationActivity {
    private PostsByLocationPresenter postsByLocationPresenter;
    private TextView location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_locatiom);

        location = findViewById(R.id.posts_by_location);

        ImageButton backBtn = findViewById(R.id.posts_by_location_back_btn);
        backBtn.setOnClickListener(v -> finish());

        postsByLocationPresenter = new PostsByLocationPresenter(this);
        int postId = getIntent().getIntExtra("post_id", 0);
        if (postId != 0)
            postsByLocationPresenter.getPostsByLocation(ACCESS_TOKEN_BEARER + HelperPref.getCurrentAccessToken(this), new PostByLocationRequest(postId));

    }

    @Override
    public void onGetPosts(PostResponse postResponse) {

        location.setText(postResponse.getPosts().get(0).getPost_locations().get(0).getAddress());

        PostByLocationAdapter adapter = new PostByLocationAdapter(postResponse.getPosts());
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
