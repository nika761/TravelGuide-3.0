package travelguideapp.ge.travelguide.ui.profile.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseFragment;
import travelguideapp.ge.travelguide.listener.PostChooseListener;
import travelguideapp.ge.travelguide.model.customModel.AppSettings;
import travelguideapp.ge.travelguide.model.parcelable.PostHomeParams;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

import static travelguideapp.ge.travelguide.model.parcelable.PostHomeParams.Type.CUSTOMER_POSTS;
import static travelguideapp.ge.travelguide.model.parcelable.PostHomeParams.Type.FAVORITES;
import static travelguideapp.ge.travelguide.model.parcelable.PostHomeParams.Type.MY_POSTS;

public class PostsFragment extends BaseFragment<PostPresenter> implements PostListener {

    public static PostsFragment getInstance(PostChooseListener callback) {
        PostsFragment userPostsFragment = new PostsFragment();
        userPostsFragment.callback = callback;
        return userPostsFragment;
    }

    private RecyclerView postsRecycler;

    private PostChooseListener callback;
    private List<PostResponse.Posts> posts;
    private PostAdapter postAdapter;
    private PostHomeParams.Type loadPageType;
    private int customerUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        postsRecycler = view.findViewById(R.id.customer_photo_recycler);
        postsRecycler.setLayoutManager(gridLayoutManager);
        postsRecycler.setHasFixedSize(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        attachPresenter(PostPresenter.with(this));
        getExtras();
    }

    private void getExtras() {
        try {
            if (posts != null)
                return;

            this.loadPageType = (PostHomeParams.Type) getArguments().getSerializable("request_type");
            if (loadPageType != null) {
                switch (loadPageType) {
                    case MY_POSTS:
                        presenter.getPosts(new PostByUserRequest(GlobalPreferences.getUserId(), 0), MY_POSTS);
                        break;

                    case CUSTOMER_POSTS:
                        this.customerUserId = getArguments().getInt("customer_user_id");
                        presenter.getPosts(new PostByUserRequest(customerUserId, 0), CUSTOMER_POSTS);
                        break;

                    case FAVORITES:
                        presenter.getPosts(new FavoritePostRequest(0), FAVORITES);
                        break;

                    case SEARCH:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecycler(List<PostResponse.Posts> posts, boolean canLazyLoad) {
        try {
            postAdapter = new PostAdapter(this);
            postAdapter.setPosts(posts);
            postAdapter.setCanLazyLoad(canLazyLoad);
            postsRecycler.setAdapter(postAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
        try {
            if (postAdapter == null) {
                if (posts.size() < AppSettings.create(GlobalPreferences.getAppSettings()).getPOST_PER_PAGE_SIZE()) {
                    initRecycler(posts, false);
                } else {
                    initRecycler(posts, true);
                }
                this.posts = posts;
            } else {
                this.posts.addAll(posts);
                postAdapter.setPosts(this.posts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLazyLoad(int postId) {
        if (presenter != null)
            switch (loadPageType) {
                case SEARCH:
                    break;

                case FAVORITES:
                    presenter.getPosts(new FavoritePostRequest(postId), FAVORITES);
                    break;

                case MY_POSTS:
                    presenter.getPosts(new PostByUserRequest(GlobalPreferences.getUserId(), postId), MY_POSTS);
                    break;

                case CUSTOMER_POSTS:
                    presenter.getPosts(new PostByUserRequest(customerUserId, postId), CUSTOMER_POSTS);
                    break;
            }
    }

    @Override
    public void onPostChoose(int position) {
        try {
//            int position = getPositionById(postId);
            PostHomeParams loadPostParams = new PostHomeParams();
            Bundle bundle = new Bundle();
            loadPostParams.setPosts(posts);
            loadPostParams.setScrollPosition(position);
            switch (loadPageType) {
                case SEARCH:
                    break;

                case FAVORITES:
                    loadPostParams.setPageType(FAVORITES);
                    bundle.putBoolean("back_to_profile", true);
                    break;

                case MY_POSTS:
                    loadPostParams.setPageType(MY_POSTS);
                    bundle.putBoolean("back_to_profile", true);
                    break;

                case CUSTOMER_POSTS:
                    loadPostParams.setPageType(CUSTOMER_POSTS);
                    loadPostParams.setUserId(customerUserId);
                    break;
            }

            bundle.putParcelable(PostHomeParams.POST_HOME_PARAMS, loadPostParams);
            callback.onPostChoose(bundle);
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
