package travelguideapp.ge.travelguide.ui.profile.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.callback.OnPostChooseCallback;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.parcelable.PostHomeParams;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;

import java.util.List;

public class PostsFragment extends Fragment implements PostListener {

    public static PostsFragment getInstance(OnPostChooseCallback callback) {
        PostsFragment userPostsFragment = new PostsFragment();
        userPostsFragment.callback = callback;
        return userPostsFragment;
    }

    private RecyclerView postsRecycler;

    private OnPostChooseCallback callback;
    private PostPresenter postPresenter;
    private List<PostResponse.Posts> posts;
    private PostAdapter postAdapter;
    private PostHomeParams.PageType loadPageType;
    private int customerUserId;
    private String accessToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_photo, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        postsRecycler = view.findViewById(R.id.customer_photo_recycler);
        postsRecycler.setLayoutManager(gridLayoutManager);
        postsRecycler.setHasFixedSize(true);

        postPresenter = new PostPresenter(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accessToken = GlobalPreferences.getAccessToken(postsRecycler.getContext());
        getExtras();
    }

    private void getExtras() {
        try {
            this.loadPageType = (PostHomeParams.PageType) getArguments().getSerializable("request_type");
            if (loadPageType != null) {
                switch (loadPageType) {
                    case MY_POSTS:
                        postPresenter.getPosts(new PostByUserRequest(GlobalPreferences.getUserId(postsRecycler.getContext()), 0), PostHomeParams.PageType.MY_POSTS);
                        break;

                    case CUSTOMER_POSTS:
                        this.customerUserId = getArguments().getInt("customer_user_id");
                        postPresenter.getPosts(new PostByUserRequest(customerUserId, 0), PostHomeParams.PageType.CUSTOMER_POSTS);
                        break;

                    case FAVORITES:
                        postPresenter.getPosts(new FavoritePostRequest(0), PostHomeParams.PageType.FAVORITES);
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
                if (posts.size() < GlobalPreferences.getAppSettings(postsRecycler.getContext()).getPOST_PER_PAGE_SIZE()) {
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
    public void onError(String message) {
        MyToaster.getToast(getContext(), message);
    }

    @Override
    public void onAuthenticationError(String message) {
        /*Supposedly TO-DO : Error when auth, redirect to sign page **/
    }

    @Override
    public void onConnectionError() {
        /*Supposedly TO-DO : No internet connection toast **/
    }

    @Override
    public void onLazyLoad(int postId) {
        if (postPresenter != null)
            switch (loadPageType) {
                case SEARCH:
                    break;

                case FAVORITES:
                    postPresenter.getPosts(new FavoritePostRequest(postId), PostHomeParams.PageType.FAVORITES);
                    break;

                case MY_POSTS:
                    postPresenter.getPosts(new PostByUserRequest(GlobalPreferences.getUserId(postsRecycler.getContext()), postId), PostHomeParams.PageType.MY_POSTS);
                    break;

                case CUSTOMER_POSTS:
                    postPresenter.getPosts(new PostByUserRequest(customerUserId, postId), PostHomeParams.PageType.CUSTOMER_POSTS);
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
                    loadPostParams.setPageType(PostHomeParams.PageType.FAVORITES);
                    bundle.putBoolean("back_to_profile", true);
                    break;

                case MY_POSTS:
                    loadPostParams.setPageType(PostHomeParams.PageType.MY_POSTS);
                    bundle.putBoolean("back_to_profile", true);
                    break;

                case CUSTOMER_POSTS:
                    loadPostParams.setPageType(PostHomeParams.PageType.CUSTOMER_POSTS);
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

    @Override
    public void onStop() {
        if (postPresenter != null) {
            postPresenter = null;
        }
        super.onStop();
    }
}
