package com.example.travelguide.ui.home.profile.favorites;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.request.FavoritePostRequest;
import com.example.travelguide.model.response.PostResponse;

import static com.example.travelguide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class FavoritePostFragment extends Fragment implements FavoritePostListener {

    private TextView tabMain, tabVideos, tabPhotos, tabAll;
    private View contentList;
    private Context context;
    private RecyclerView recyclerView;
    private boolean visible = true;
    private FavoritePostPresenter favoritePostPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_p_liked, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("lifecycle", "onviewcreated");
        iniUI(view);
        favoritePostPresenter.getFavoritePosts(ACCESS_TOKEN_BEARER + HelperPref.getAccessToken(context), new FavoritePostRequest(0));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e("lifecycle", "onatach");
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e("lifecycle", "onaccreated");
        super.onActivityCreated(savedInstanceState);
    }

    private void iniUI(View v) {

        favoritePostPresenter = new FavoritePostPresenter(this);

        tabMain = v.findViewById(R.id.tab_liked_main);
        tabMain.setOnClickListener(this::onTabItemClick);

        contentList = v.findViewById(R.id.tab_liked_main_cont);

        recyclerView = v.findViewById(R.id.favorite_post_recycler);

        tabVideos = v.findViewById(R.id.tab_liked_video);
        tabVideos.setOnClickListener(this::onTabItemClick);

        tabPhotos = v.findViewById(R.id.tab_liked_photo);
        tabPhotos.setOnClickListener(this::onTabItemClick);

        tabAll = v.findViewById(R.id.tab_liked_all);
        tabAll.setOnClickListener(this::onTabItemClick);

    }

    private void loadAnimation(View target, int animationId, int offset) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setStartOffset(offset);
        target.startAnimation(animation);
    }

    private void setTabTextColor(TextView textView, int color) {
        textView.setTextColor(getResources().getColor(color));
    }

    private void onTabItemClick(View v) {
        switch (v.getId()) {

            case R.id.tab_liked_main:
                if (visible) {
                    contentList.setVisibility(View.VISIBLE);
                    visible = false;
                } else {
                    contentList.setVisibility(View.GONE);
                    visible = true;
                }
                break;

            case R.id.tab_liked_video:
                tabMain.setText("Videos");
                setTabTextColor(tabVideos, R.color.yellowTextView);
                setTabTextColor(tabPhotos, R.color.black);
                setTabTextColor(tabAll, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;

                break;

            case R.id.tab_liked_photo:
                tabMain.setText("Photos");
                setTabTextColor(tabPhotos, R.color.yellowTextView);
                setTabTextColor(tabVideos, R.color.black);
                setTabTextColor(tabAll, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;

                break;

            case R.id.tab_liked_all:
                tabMain.setText("All");
                setTabTextColor(tabAll, R.color.yellowTextView);
                setTabTextColor(tabPhotos, R.color.black);
                setTabTextColor(tabVideos, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;

                break;
        }
    }

    @Override
    public void onGetPosts(PostResponse postResponse) {
        if (postResponse.getStatus() == 0) {
            FavoritePostAdapter adapter = new FavoritePostAdapter(postResponse.getPosts(), this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(context, "Error while loading", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPostsError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostChoose(int postId) {

    }

    @Override
    public void onPause() {
        Log.e("lifecycle", "onpause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.e("lifecycle", "onresume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (favoritePostPresenter != null) {
            favoritePostPresenter = null;
        }
        Log.e("lifecycle", "ondestroy");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.e("lifecycle", "onstop");
        super.onStop();
    }

    @Override
    public void onStart() {
        Log.e("lifecycle", "onstart");
        super.onStart();
    }
}
