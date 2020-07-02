package com.example.travelguide.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.travelguide.R;
import com.example.travelguide.activity.ChooseLanguageActivity;
import com.example.travelguide.activity.SplashScreenActivity;
import com.example.travelguide.model.User;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.utils.UtilsGlide;

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
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        initUI(view);
        onGetData();
        setClickListeners();
        startStory();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
        storyLike.setOnClickListener(v -> storyLike.setBackground(getResources().getDrawable(R.drawable.heart_emoji_red)));
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
        storiesProgressView.setStoriesCount(6); // <- set stories
        storiesProgressView.setStoryDuration(5000); // <- set a story duration
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
        if (getArguments() != null && getArguments().containsKey("user")) {
            LoginResponseModel.User serverUser = (LoginResponseModel.User) getArguments().getSerializable("user");
            if (serverUser != null) {
                setUserData(serverUser);
            }
//            User user = (User) getArguments().getSerializable("user");
//            if (user != null)
//                setUserData(user);
        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUserData(LoginResponseModel.User u) {
//        if (u.getUrl() != null) {
//            UtilsGlide.loadPhoto(context, u.getUrl(), userLeftImage);
//        }
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
