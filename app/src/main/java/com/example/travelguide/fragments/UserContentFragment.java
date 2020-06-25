package com.example.travelguide.fragments;

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

public class UserContentFragment extends Fragment {

    private TextView tabMain, tabVides, tabPhotos, tabAll;
    private View contentList;
    private Context context;
    private boolean visible = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_liked, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniUI(view);
        setClickListeners();
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
        contentList = v.findViewById(R.id.tab_liked_main_cont);
        tabVides = v.findViewById(R.id.tab_liked_video);
        tabPhotos = v.findViewById(R.id.tab_liked_photo);
        tabAll = v.findViewById(R.id.tab_liked_all);
    }

    private void setClickListeners() {
        tabMain.setOnClickListener(this::onTabItemClick);
        tabVides.setOnClickListener(this::onTabItemClick);
        tabPhotos.setOnClickListener(this::onTabItemClick);
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
                setTabTextColor(tabVides, R.color.yellowTextView);
                setTabTextColor(tabPhotos, R.color.black);
                setTabTextColor(tabAll, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;
                break;

            case R.id.tab_liked_photo:
                tabMain.setText("Photos");
                setTabTextColor(tabPhotos, R.color.yellowTextView);
                setTabTextColor(tabVides, R.color.black);
                setTabTextColor(tabAll, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;
                break;

            case R.id.tab_liked_all:
                tabMain.setText("All");
                setTabTextColor(tabAll, R.color.yellowTextView);
                setTabTextColor(tabPhotos, R.color.black);
                setTabTextColor(tabVides, R.color.black);
                contentList.setVisibility(View.GONE);
                visible = true;
                break;
        }
    }

}
