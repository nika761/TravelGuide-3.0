package travelguideapp.ge.travelguide.ui.search;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BasePresenterActivity;
import travelguideapp.ge.travelguide.base.HomeParentActivity;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.model.parcelable.PostHomeParams;
import travelguideapp.ge.travelguide.model.request.FullSearchRequest;
import travelguideapp.ge.travelguide.model.request.PostByLocationRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragment;
import travelguideapp.ge.travelguide.ui.search.go.GoFragment;
import travelguideapp.ge.travelguide.ui.search.hashtag.HashtagsFragment;
import travelguideapp.ge.travelguide.ui.search.posts.SearchPostFragment;
import travelguideapp.ge.travelguide.ui.search.user.UsersFragment;

public class SearchActivity extends BasePresenterActivity<SearchPresenter> implements SearchListener {

    private ImageButton searchBtn, backBtn, clearTextBtn;
    private TabLayout tabLayout;
    private EditText searchField;
    private ConstraintLayout loader;

    private SearchPostFragment searchPostsFragment;
    private HashtagsFragment searchHashtagsFragment;
    private UsersFragment searchUsersFragment;
    private GoFragment searchGoFragment;

    private List<HashtagResponse.Hashtags> hashtags;
    private List<FullSearchResponse.Users> users;
    private List<PostResponse.Posts> posts;

    private String searchedText;
    private boolean isLazyLoadHashtag, isLazyLoadPosts, isLazyLoadUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        attachPresenter(SearchPresenter.with(this));
        initUI();
    }

    private void initUI() {
        ViewPager viewPager = findViewById(R.id.search_view_pager);

        tabLayout = findViewById(R.id.search_tabs);

        searchField = findViewById(R.id.search_edit_text_second);
        searchField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                startSearch();
                return true;
            }
            return false;
        });

        backBtn = findViewById(R.id.search_back_btn_second);
        backBtn.setOnClickListener(v -> finish());

        clearTextBtn = findViewById(R.id.search_clear_searched_text_btn);
        clearTextBtn.setOnClickListener(v -> clearPreviousText());

        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(v -> startSearch());

        setViewPager(viewPager);

    }

    private void clearPreviousText() {
        try {
            if (searchField.getText().toString() != null) {
                if (!searchField.getText().toString().isEmpty()) {
                    searchField.getText().clear();
                    searchField.clearFocus();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSearch() {
        try {
            if (searchField.getText().toString() != null && !searchField.getText().toString().isEmpty()) {
                String requestText = searchField.getText().toString();
                searchField.clearFocus();
                presenter.fullSearch(new FullSearchRequest(requestText));
                this.searchedText = requestText;
                isLazyLoadHashtag = false;
                isLazyLoadPosts = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setViewPager(ViewPager viewPager) {
        searchHashtagsFragment = new HashtagsFragment();
        searchUsersFragment = new UsersFragment();
        searchPostsFragment = new SearchPostFragment();

        SearchPagerAdapter searchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        searchPagerAdapter.addFragment(searchUsersFragment, getString(R.string.users_tab));
        searchPagerAdapter.addFragment(searchPostsFragment, getString(R.string.location));
        searchPagerAdapter.addFragment(searchHashtagsFragment, getString(R.string.hashtags_tab));
        viewPager.setAdapter(searchPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//                        Log.e("asdzxc", String.valueOf(tab.getId()));
//                        break;
//                    case 1:
//                        Log.e("asdzxc", String.valueOf(tab.getId()));
//                        break;
//                    case 2:
//                        Log.e("asdzxc", String.valueOf(tab.getId()));
//                        break;
//                    case 3:
//                        Log.e("asdzxc", String.valueOf(tab.getId()));
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//
//        });

    }

    @Override
    public void onGetHashtags(List<HashtagResponse.Hashtags> hashtags) {
        if (isLazyLoadHashtag) {
            try {
                isLazyLoadHashtag = false;
                this.hashtags.addAll(hashtags);
                searchHashtagsFragment.setLazyHashtags(this.hashtags);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGetUsers(List<FollowerResponse.Followers> followers) {
//        try {
//            if (this.followers == null) {
//                this.followers = followers;
//                searchUsersFragment.initUsersRecycler(this.followers);
//                getLoader(false);
//            } else {
//                this.followers.addAll(followers);
//                searchUsersFragment.setUsers(this.followers);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            getLoader(false);
//            this.followers = followers;
//            searchUsersFragment.setUsers(this.followers);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
        if (isLazyLoadPosts) {
            try {
                isLazyLoadPosts = false;
                this.posts.addAll(posts);
                searchPostsFragment.setLazyPosts(this.posts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGetSearchedData(FullSearchResponse fullSearchResponse) {
        try {
            if (fullSearchResponse.getUsers() != null && fullSearchResponse.getUsers().size() > 0) {
                this.users = fullSearchResponse.getUsers();
                searchUsersFragment.setUsers(this.users);
            } else {
                this.users = null;
                searchUsersFragment.setNothingFound();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (fullSearchResponse.getHashtags() != null && fullSearchResponse.getHashtags().size() > 0) {
                this.hashtags = fullSearchResponse.getHashtags();
                searchHashtagsFragment.setHashtags(this.hashtags);
            } else {
                this.hashtags = null;
                searchHashtagsFragment.setNothingFound();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (fullSearchResponse.getPosts() != null && fullSearchResponse.getPosts().size() > 0) {
                this.posts = fullSearchResponse.getPosts();
                searchPostsFragment.setPosts(this.posts);
            } else {
                this.posts = null;
                searchPostsFragment.setNothingFound();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getHashtagsNextPage(int page) {
        try {
            isLazyLoadHashtag = true;
            presenter.getHashtags(new SearchHashtagRequest(searchedText, page));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPostsNextPage(int postId) {
        try {
            isLazyLoadPosts = true;
            presenter.getPosts(new PostByLocationRequest(postId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<FullSearchResponse.Users> getUsers() {
        if (users == null)
            return null;
        else
            return users;
    }

    public List<HashtagResponse.Hashtags> getHashtags() {
        if (hashtags == null)
            return null;
        else
            return hashtags;
    }

    public List<PostResponse.Posts> getPosts() {
        if (posts == null)
            return null;
        else
            return posts;
    }

    public void onChoosePost(int postId) {
        try {
            int position = getPositionById(postId);

            PostHomeParams postDataLoad = new PostHomeParams();
            postDataLoad.setPageType(PostHomeParams.Type.SEARCH);
            postDataLoad.setScrollPosition(position);
            postDataLoad.setPosts(posts);

            Bundle data = new Bundle();
            data.putParcelable(PostHomeParams.POST_HOME_PARAMS, postDataLoad);

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


}
