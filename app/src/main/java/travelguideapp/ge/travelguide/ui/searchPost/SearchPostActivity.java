package travelguideapp.ge.travelguide.ui.searchPost;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.SearchPostBy;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.PostByHashtagRequest;
import travelguideapp.ge.travelguide.model.request.PostByLocationRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;


public class SearchPostActivity extends AppCompatActivity implements SearchPostListener {

    private SearchPostPresenter searchPostPresenter;
    private TextView head;

    private SearchPostBy type;
    private String hashtag;
    private int postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_locatiom);

        getExtras();

        searchPostPresenter = new SearchPostPresenter(this);

        initUI();

        switch (type) {
            case LOCATION:
                searchPostPresenter.getPostsByLocation(GlobalPreferences.getAccessToken(this), new PostByLocationRequest(postId));
                break;
            case HASHTAG:
                searchPostPresenter.getPostsByHashtag(GlobalPreferences.getAccessToken(this), new PostByHashtagRequest(hashtag));
                break;
        }
    }

    private void initUI() {
        head = findViewById(R.id.posts_by_location);

        ImageButton backBtn = findViewById(R.id.posts_by_location_back_btn);
        backBtn.setOnClickListener(v -> finish());
    }

    private void getExtras() {
        this.postId = getIntent().getIntExtra("search_post_id", 0);
        this.hashtag = getIntent().getStringExtra("search_hashtag");
        this.type = (SearchPostBy) getIntent().getSerializableExtra("search_type");
    }

    @Override
    public void onGetPosts(PostResponse postResponse) {
        try {
            switch (type) {
                case LOCATION:
                    if (postResponse.getPosts() != null)
                        if (postResponse.getPosts().size() != 0)
                            head.setText(postResponse.getPosts().get(0).getPost_locations().get(0).getAddress());
                    break;

                case HASHTAG:
                    head.setText(hashtag);
                    break;
            }

            SearchPostAdapter adapter = new SearchPostAdapter(postResponse.getPosts());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            RecyclerView recyclerView = findViewById(R.id.posts_by_location_recycler);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onGetPostError(String message) {
        MyToaster.getErrorToaster(this, message);
    }

}
