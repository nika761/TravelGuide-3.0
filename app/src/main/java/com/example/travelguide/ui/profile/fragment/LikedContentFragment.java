package com.example.travelguide.ui.profile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelguide.R;

public class LikedContentFragment extends Fragment {

    private TextView tabMain, tabVideos, tabPhotos, tabAll;
    private View contentList;
    private Context context;
    private boolean visible = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_p_liked, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniUI(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void iniUI(View v) {
        tabMain = v.findViewById(R.id.tab_liked_main);
        tabMain.setOnClickListener(this::onTabItemClick);

        contentList = v.findViewById(R.id.tab_liked_main_cont);

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

}