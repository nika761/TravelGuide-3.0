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
import travelguideapp.ge.travelguide.model.parcelable.HomePostParams;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.utility.Navigation;

import static travelguideapp.ge.travelguide.model.parcelable.HomePostParams.Type.CUSTOMER_POSTS;
import static travelguideapp.ge.travelguide.model.parcelable.HomePostParams.Type.FAVORITES;
import static travelguideapp.ge.travelguide.model.parcelable.HomePostParams.Type.MY_POSTS;

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
    private HomePostParams.Type pageType;
    private int customerUserId;

    private int pageSize;

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

            this.pageType = (HomePostParams.Type) getArguments().getSerializable("request_type");

            if (pageType == null) {
                return;
            }

            this.pageSize = AppSettings.create(GlobalPreferences.getAppSettings()).getPostPerPage();

            if (pageSize == 0) {
                return;
            }

            if (getArguments().containsKey("customer_user_id")) {
                this.customerUserId = getArguments().getInt("customer_user_id");
            }

            getPostsByPageType(pageType, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPostsByPageType(HomePostParams.Type pageType, int fromPostId) {
        switch (pageType) {
            case MY_POSTS:
                presenter.getPosts(new PostByUserRequest(GlobalPreferences.getUserId(), fromPostId), MY_POSTS);
                break;

            case CUSTOMER_POSTS:
                presenter.getPosts(new PostByUserRequest(customerUserId, fromPostId), CUSTOMER_POSTS);
                break;

            case FAVORITES:
                presenter.getPosts(new FavoritePostRequest(fromPostId), FAVORITES);
                break;

            case SEARCH:
                break;
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
                initRecycler(posts, posts.size() >= pageSize);
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
        try {
            if (presenter == null || pageType == null) {
                return;
            }

            getPostsByPageType(pageType, postId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostChoose(int position) {
        try {
//            int position = getPositionById(postId);
            HomePostParams postHomeParams = new HomePostParams();
            Bundle bundle = new Bundle();
            postHomeParams.setPosts(posts);
            postHomeParams.setScrollPosition(position);
            switch (pageType) {
                case SEARCH:
                    break;

                case FAVORITES:
                    postHomeParams.setPageType(FAVORITES);
                    postHomeParams.setNavigation(Navigation.BACK_TO_PROFILE);
                    bundle.putBoolean(Navigation.BACK_TO_PROFILE, true);
                    break;

                case MY_POSTS:
                    postHomeParams.setPageType(MY_POSTS);
                    postHomeParams.setNavigation(Navigation.BACK_TO_PROFILE);
                    bundle.putBoolean(Navigation.BACK_TO_PROFILE, true);
                    break;

                case CUSTOMER_POSTS:
                    postHomeParams.setPageType(CUSTOMER_POSTS);
                    postHomeParams.setUserId(customerUserId);
                    break;
            }
            bundle.putParcelable(HomePostParams.POST_HOME_PARAMS, postHomeParams);
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
