package com.example.travelguide.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.travelguide.R;
import com.example.travelguide.activity.SavedUserActivity;
import com.example.travelguide.activity.UserPageActivity;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileFragment extends Fragment {

    private TextView nickName, name, userBioText, drawerBtn, tourRequest, firstPhotoConent;
    private ImageButton userContentPhotoBtn, userContentMiddleBtn, userContentTourBtn;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5,
            imageView6, imageView7, imageView8, imageView9;
    private CircleImageView userPrfImage;
    private ImageView userBioTextState;
    private LinearLayout firstPhotoLinear, linearTour;
    private NestedScrollView nestedScrollView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private String loginType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initToolbarNav(view);
        initBurgerNav(view);
        onGetData();
        setClickListeners();
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
        loginType = getArguments().getString("loginType");

        name.setText(firstName + " " + lastName);
        nickName.setText("@" + "" + firstName + lastName);
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
                    .into(userPrfImage);
        }

    }

    private void initUI(View view) {
        name = view.findViewById(R.id.user_prf_name);
        drawerBtn = view.findViewById(R.id.drawer_button);
        nickName = view.findViewById(R.id.user_prf_nickName);
        userPrfImage = view.findViewById(R.id.user_prf_image);
        tourRequest = view.findViewById(R.id.tour_request_background);
        firstPhotoConent = view.findViewById(R.id.first_photo_content_tab);
        userContentPhotoBtn = view.findViewById(R.id.user_content_photo_fr);
        userContentMiddleBtn = view.findViewById(R.id.user_content_middle_fr);
        userContentTourBtn = view.findViewById(R.id.user_content_tour_fr);
        tabLayout = view.findViewById(R.id.tabs_profile);
        nestedScrollView = view.findViewById(R.id.scrollView_photos);
        linearTour = view.findViewById(R.id.tour_request);
        firstPhotoLinear = view.findViewById(R.id.first_photo_content);
        setContentAnim(firstPhotoConent);
        imageView1 = view.findViewById(R.id.image_1);
        imageView2 = view.findViewById(R.id.image_2);
        imageView3 = view.findViewById(R.id.image_3);
        imageView4 = view.findViewById(R.id.image_4);
        imageView5 = view.findViewById(R.id.image_5);
        imageView6 = view.findViewById(R.id.image_6);
        imageView7 = view.findViewById(R.id.image_7);
        imageView8 = view.findViewById(R.id.image_8);
        imageView9 = view.findViewById(R.id.image_9);
    }

    private void setClickListeners() {
        userContentPhotoBtn.setOnClickListener(this::onContentIconClick);
        userContentMiddleBtn.setOnClickListener(this::onContentIconClick);
        userContentTourBtn.setOnClickListener(this::onContentIconClick);
    }

    private void initToolbarNav(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        toolbar.setTitle("");
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.addDrawerListener(toggle);
            drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    private void initBurgerNav(View view) {
        NavigationView navigationView = view.findViewById(R.id.burger_navigation_view);
        navigationView.setNavigationItemSelectedListener(navListener);
    }

    private NavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment;

        switch (item.getItemId()) {
            case R.id.settings_share_profile:
                Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_balance:
                Toast.makeText(getContext(), "Balance", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_language:
                Toast.makeText(getContext(), "Language", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_about:
                Toast.makeText(getContext(), "About", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_terms:
                Toast.makeText(getContext(), "Terms", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_sing_out:
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Log out ?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            if (loginType.equals("facebook")) {
                                ((UserPageActivity) Objects.requireNonNull(getActivity())).signOutFromFbAccount();
                            } else if (loginType.equals("google")) {
                                ((UserPageActivity) Objects.requireNonNull(getActivity())).signOutFromGoogleAccount();
                            }
//                            Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .create();
                alertDialog.show();
                break;
        }

        return true;
    };

    private void setAnimation(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_languages);
        v.setAnimation(animation);
    }

    private void setContentAnim(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_photo_content_tab);
        v.setAnimation(animation);
    }

    private void onContentIconClick(View v) {
        switch (v.getId()) {

            case R.id.user_content_photo_fr:
                userContentPhotoBtn.setBackground(getResources().getDrawable(R.drawable.profile_photos_pressed));
                userContentTourBtn.setBackground(getResources().getDrawable(R.drawable.profile_tour));
                userContentMiddleBtn.setBackground(getResources().getDrawable(R.drawable.profile_liked));
                firstPhotoConent.setVisibility(View.VISIBLE);
                linearTour.setVisibility(View.GONE);
                setContentAnim(firstPhotoConent);
                break;

            case R.id.user_content_middle_fr:
                userContentMiddleBtn.setBackground(getResources().getDrawable(R.drawable.profile_liked_pressed));
                userContentPhotoBtn.setBackground(getResources().getDrawable(R.drawable.profile_photos));
                userContentTourBtn.setBackground(getResources().getDrawable(R.drawable.profile_tour));
                Toast.makeText(getContext(), "Middle Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.user_content_tour_fr:
                userContentTourBtn.setBackground(getResources().getDrawable(R.drawable.profile_tour_pressed));
                userContentPhotoBtn.setBackground(getResources().getDrawable(R.drawable.profile_photos));
                userContentMiddleBtn.setBackground(getResources().getDrawable(R.drawable.profile_liked));
                firstPhotoConent.setVisibility(View.GONE);
                linearTour.setVisibility(View.VISIBLE);
                setAnimation(tourRequest);
                break;
        }
    }
}
