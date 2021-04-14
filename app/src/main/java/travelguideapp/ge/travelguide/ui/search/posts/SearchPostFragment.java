package travelguideapp.ge.travelguide.ui.search.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;

public class SearchPostFragment extends Fragment implements SearchPostAdapter.ChoosePostCallback {

    private RecyclerView postRecycler;
    private SearchPostAdapter searchPostAdapter;
    private LinearLayout nothingFound;

    private boolean isLoading;
    private boolean isFirstOpen = true;

    private List<PostResponse.Posts> posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_stories, container, false);
        nothingFound = view.findViewById(R.id.nothing_found_users);
        postRecycler = view.findViewById(R.id.search_post_fragment_recycler);
        postRecycler.setLayoutManager(new GridLayoutManager(postRecycler.getContext(), 3));
        postRecycler.setHasFixedSize(true);

        searchPostAdapter = new SearchPostAdapter(this);
        return view;
    }

    public void setNothingFound() {
        try {
            postRecycler.setVisibility(View.GONE);
            nothingFound.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenOnRecycler() {
        try {
            postRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (!isLoading) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == posts.size() - 1) {
                            onLazyLoad(posts.get(posts.size() - 1).getPost_id());
                            isLoading = true;
                        }
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setPosts(List<PostResponse.Posts> posts) {
        try {
            nothingFound.setVisibility(View.GONE);
            postRecycler.setVisibility(View.VISIBLE);
            this.posts = posts;
            searchPostAdapter.setPosts(this.posts);
            if (postRecycler.getAdapter() == null)
                postRecycler.setAdapter(searchPostAdapter);
//            listenOnRecycler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLazyLoad(int postId) {
        try {
            ((SearchActivity) postRecycler.getContext()).getPostsNextPage(postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getCachedPosts();
    }

    public void setLazyPosts(List<PostResponse.Posts> posts) {
        try {
            isLoading = false;
            this.posts = posts;
            searchPostAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCachedPosts() {
        try {
            List<PostResponse.Posts> posts = ((SearchActivity) postRecycler.getContext()).getPosts();
            if (posts != null) {
                setPosts(posts);
                if (isFirstOpen)
                    isFirstOpen = false;
            } else {
                if (!isFirstOpen) {
                    setNothingFound();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChoosePost(int postId) {
        try {
            ((SearchActivity) postRecycler.getContext()).onChoosePost(postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
