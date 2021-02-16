package travelguideapp.ge.travelguide.ui.search.posts;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.parcelable.PostDataLoad;
import travelguideapp.ge.travelguide.model.parcelable.PostDataSearch;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragment;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.PostByHashtagRequest;
import travelguideapp.ge.travelguide.model.request.PostByLocationRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;

public class PostByLocationActivity extends BaseActivity implements PostByLocationListener, SearchPostAdapter.ChoosePostCallback {

    private PostByLocationPresenter searchPostPresenter;
    private TextView head;

    private List<PostResponse.Posts> posts;

    private PostDataSearch.SearchBy searchBy;
    private String hashtag;
    private int postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_locatiom);

        searchPostPresenter = new PostByLocationPresenter(this);

        getExtras();

        initUI();

        getPosts();

    }

    private void initUI() {
        head = findViewById(R.id.posts_by_location);

        ImageButton backBtn = findViewById(R.id.posts_by_location_back_btn);
        backBtn.setOnClickListener(v -> finish());
    }

    public void setHead(String headTxt) {
        if (headTxt != null)
            head.setText(headTxt);
    }

    private void getExtras() {
        try {
            PostDataSearch postDataSearch = getIntent().getParcelableExtra(PostDataSearch.INTENT_KEY_SEARCH);
            this.searchBy = postDataSearch.getSearchBy();
            switch (searchBy) {
                case HASHTAG:
                    this.hashtag = postDataSearch.getHashtag();
                    break;

                case LOCATION:
                    this.postId = postDataSearch.getPostId();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private void getPosts() {
        switch (searchBy) {
            case LOCATION:
                searchPostPresenter.getPostsByLocation(GlobalPreferences.getAccessToken(this), new PostByLocationRequest(postId));
                break;
            case HASHTAG:
                searchPostPresenter.getPostsByHashtag(GlobalPreferences.getAccessToken(this), new PostByHashtagRequest(hashtag, 0));
                break;
        }
    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
        try {
            switch (searchBy) {
                case LOCATION:
                    setHead(posts.get(0).getPost_locations().get(0).getAddress());
                    break;

                case HASHTAG:
                    setHead(hashtag);
                    break;
            }

            this.posts = posts;

            SearchPostAdapter adapter = new SearchPostAdapter(this);
            adapter.setPosts(posts);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            RecyclerView recyclerView = findViewById(R.id.posts_by_location_recycler);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onChoosePost(int postId) {
        try {
            int position = getPositionById(postId);

            PostDataLoad postDataLoad = new PostDataLoad();
            postDataLoad.setLoadSource(PostDataLoad.Source.SEARCH);
            postDataLoad.setScrollPosition(position);
            postDataLoad.setPosts(posts);

            Bundle data = new Bundle();
            data.putParcelable(PostDataLoad.INTENT_KEY_LOAD, postDataLoad);

            HelperUI.loadFragment(HomeFragment.getInstance(), data, R.id.home_fragment_container, true, true, this);

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
    public void onGetPostError(String message) {
        MyToaster.getToast(this, message);
    }

    @Override
    protected void onDestroy() {
        if (searchPostPresenter != null)
            searchPostPresenter = null;
        super.onDestroy();
    }
}
