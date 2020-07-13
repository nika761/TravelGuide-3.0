package com.example.travelguide.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travelguide.R;
import com.example.travelguide.adapter.recycler.PostAdapter;
import com.example.travelguide.model.Post;
import com.example.travelguide.model.response.LoginResponseModel;

import java.util.ArrayList;
import java.util.List;
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
    private ArrayList<String> postList = new ArrayList<>();
    private ViewPager2 viewPager2;
    private TypedArray myImages;

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
        viewPager2 = view.findViewById(R.id.post_view_pager);
        myImages = getResources().obtainTypedArray(R.array.background_images);
        setPostsData();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setPostsData() {
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.e_image_1);
//        postList.add(bitmap);
//        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e_image_2);
//        postList.add(bitmap1);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e_image_3);
//        postList.add(bitmap2);
//        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e_image_4);
//        postList.add(bitmap3);
//        Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e_image_5);
//        postList.add(bitmap4);
//        Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.e_image_6);
//        postList.add(bitmap5);
//
        String videoUri = "/storage/emulated/0/Download/1594545831519_20200711_004519.mp4";
        String videoUri1 = "/storage/emulated/0/Download/1594545831519_20200711_004519.mp4";
        String videoUri4 = "/storage/emulated/0/Download/1594545831519_20200711_004519.mp4";
        String videoUri3 = "/storage/emulated/0/Download/1594545831519_20200711_004519.mp4";

        postList.add(videoUri);
        postList.add(videoUri1);
        postList.add(videoUri3);
        postList.add(videoUri4);


        if (postList != null) {
            viewPager2.setAdapter(new PostAdapter(postList));
        }
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
        storyLike.setOnClickListener(v -> storyLike.setBackground(getResources().getDrawable(R.drawable.emoji_heart_red)));
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
        final Random random = new Random();
        int randomInt = random.nextInt(myImages.length());
        return myImages.getResourceId(randomInt, -1);
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
}
