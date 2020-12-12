package com.travel.guide.ui.home.profile.favorites;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.utility.GlobalPreferences;
import com.travel.guide.model.request.FavoritePostRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.ui.home.profile.ProfileFragment;

import java.io.Serializable;
import java.util.List;

import static com.travel.guide.enums.GetPostsFrom.FAVORITES;

public class FavoritePostFragment extends Fragment implements FavoritePostListener {

    private Context context;
    private RecyclerView recyclerView;

    private FavoritePostAdapter favoritePostAdapter;

    private List<PostResponse.Posts> posts;
    private FavoritePostPresenter favoritePostPresenter;
    private ProfileFragment.OnPostChooseListener listener;

    private LottieAnimationView loader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView = view.findViewById(R.id.favorite_post_recycler);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        loader = view.findViewById(R.id.favorite_paging_loader);

        favoritePostPresenter = new FavoritePostPresenter(this);

        try {
            this.listener = (ProfileFragment.OnPostChooseListener) context;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritePostPresenter.getFavoritePosts(GlobalPreferences.getAccessToken(context), new FavoritePostRequest(0));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initRecycler(List<PostResponse.Posts> posts) {

        favoritePostAdapter = new FavoritePostAdapter(this);

        int itemWidth = HelperMedia.getScreenWidth(getActivity());
        if (itemWidth != 0)
            favoritePostAdapter.setItemWidth(itemWidth);

        favoritePostAdapter.setPosts(posts);
        recyclerView.setAdapter(favoritePostAdapter);
    }


    @Override
    public void onGetPosts(List<PostResponse.Posts> posts) {
        try {
            if (favoritePostAdapter == null) {
                initRecycler(posts);
                this.posts = posts;
            } else {
                this.posts.addAll(posts);
                favoritePostAdapter.setPosts(this.posts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        try {
//            if (this.posts == null) {
//                this.posts = posts;
//            } else {
//                this.posts.addAll(posts);
//            }
//
//            if (favoritePostAdapter == null)
//                initRecycler(posts);
//            else
//                favoritePostAdapter.setPosts(this.posts);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    @Override
    public void onGetPostsError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLazyLoad(int postId) {
        favoritePostPresenter.getFavoritePosts(GlobalPreferences.getAccessToken(context), new FavoritePostRequest(postId));
    }

    @Override
    public void onPostChoose(int postId) {
        int position = getPositionById(postId);

        Bundle data = new Bundle();
        data.putInt("postPosition", position);
        data.putSerializable("PostShowType", FAVORITES);
        data.putSerializable("favoritePosts", (Serializable) posts);
        listener.onPostChoose(data);

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
    public void onDestroy() {
        if (favoritePostPresenter != null) {
            favoritePostPresenter = null;
        }
        super.onDestroy();
    }
}
