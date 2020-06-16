package com.example.travelguide.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.travelguide.R;
import com.example.travelguide.activity.ChooseLanguageActivity;
import com.example.travelguide.activity.SplashScreenActivity;

import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.shts.android.storiesprogressview.StoriesProgressView;

public class UserHomeFragment extends Fragment {
    private CircleImageView userLeftImage;
    private StoriesProgressView storiesProgressView;
    private ImageButton storyLike;
    private ImageView imageView;
    private final int STORY_DISPLAY_ONE = 10000;
    private final int STORY_DISPLAY_TWO = 20000;
    private boolean checked = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        onGetData();
        setClickListeners();
        startStory();
    }

    private void initUI(View view) {
        storiesProgressView = view.findViewById(R.id.stories);
        userLeftImage = view.findViewById(R.id.user_photo_left);
        imageView = view.findViewById(R.id.image_story);
        storyLike = view.findViewById(R.id.story_like);
    }

    private void setClickListeners() {
        imageView.setOnClickListener(v -> startStory());
        imageView.setImageResource(setStoryPhoto());
        storyLike.setOnClickListener(v -> {
            storyLike.setBackground(getResources().getDrawable(R.drawable.like_presed));
        });
    }

//    private void checkStory() {
//        if (checked) {
//            new Handler().postDelayed(this::startStory, STORY_DISPLAY_ONE);
//        }
//
//        if (checked) {
//            new Handler().postDelayed(this::startStory, STORY_DISPLAY_TWO);
//        }
//    }

    private void startStory() {
        storiesProgressView.setStoriesCount(4); // <- set stories
        storiesProgressView.setStoryDuration(10000); // <- set a story duration
        storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
            @Override
            public void onNext() {
                imageView.setImageResource(setStoryPhoto());
                storiesProgressView.destroy();
            }

            @Override
            public void onPrev() {

            }

            @Override
            public void onComplete() {
                Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
                storiesProgressView.destroy();
            }
        }); // <- set listener
        storiesProgressView.startStories();
    }

    private int setStoryPhoto() {
        Resources res = getResources();
        @SuppressLint("Recycle") final TypedArray myImages = res.obtainTypedArray(R.array.background_images);
        final Random random = new Random();
        int randomInt = random.nextInt(myImages.length());
        int drawableID = myImages.getResourceId(randomInt, -1);

        return drawableID;
    }

    private void onGetData() {
        if (checkUserData(getArguments())) {
            setUserData();
        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkUserData(Bundle arguments) {
        boolean onGetData = true;

        if (arguments == null) {
            onGetData = false;
        }
        return onGetData;
    }

    private void setUserData() {
        String firstName = getArguments().getString("firstName");
        String lastName = getArguments().getString("lastName");
        String url = getArguments().getString("url");
        String id = getArguments().getString("id");
        String email = getArguments().getString("email");
        String loginType = getArguments().getString("loginType");

        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(userLeftImage);
        }
    }

    @Override
    public void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        storiesProgressView.pause();
        checked = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        storiesProgressView.resume();
        super.onResume();
    }
}
