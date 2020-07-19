package com.example.travelguide.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.PostAdapter;
import com.example.travelguide.adapter.recycler.VideoPlayerRecyclerAdapter;
import com.example.travelguide.model.Post;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.utils.UtilsMedia;
import com.example.travelguide.utils.UtilsPermissions;
import com.example.travelguide.utils.VideoPlayerRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserHomeFragment extends Fragment {
    private ViewPager2 viewPager2;
    private int oldPosition;
    private int currentPosition;
    private List<Post> posts = new ArrayList<>();
    private VideoPlayerRecyclerView videoPlayerRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        View decorView = window.getDecorView();
//        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewPager2 = view.findViewById(R.id.post_view_pager);
        videoPlayerRecyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void setPostsData() {
        String path = "/storage/emulated/0/DCIM/Camera/2020-07-12-104752811.mp4";

        ArrayList<String> arrayList = UtilsMedia.getVideosPathByDate(viewPager2.getContext());
        viewPager2.setAdapter(new PostAdapter(arrayList));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                scrollListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void scrollListener(int position) {
        oldPosition = currentPosition;
        currentPosition = position;
        if (oldPosition > currentPosition) {
            Log.e("scroll", String.valueOf(position));
            Log.e("scroll", String.valueOf(oldPosition));
        } else {
            Log.e("scroll", String.valueOf(position));
            Log.e("scroll", String.valueOf(currentPosition));
        }
    }

    private void onGetData() {
        if (getArguments() != null && getArguments().containsKey("user")) {
            LoginResponseModel.User serverUser = (LoginResponseModel.User) getArguments().getSerializable("user");
            if (serverUser != null) {
            }
//            User user = (User) getArguments().getSerializable("user");
//            if (user != null)
//                setUserData(user);
        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        videoPlayerRecyclerView.setLayoutManager(layoutManager);
        ArrayList<String> mediaObjects = UtilsMedia.getVideosPathByDate(getContext());
        videoPlayerRecyclerView.setMediaObjects(mediaObjects);
        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(mediaObjects, initGlide());
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(videoPlayerRecyclerView);
        videoPlayerRecyclerView.setAdapter(adapter);
    }


    private RequestManager initGlide() {

        RequestOptions options = new RequestOptions();
        return Glide.with(this).setDefaultRequestOptions(options);

    }

    @Override
    public void onPause() {
        if (videoPlayerRecyclerView != null)
            videoPlayerRecyclerView.releasePlayer();
        super.onPause();
    }
}
