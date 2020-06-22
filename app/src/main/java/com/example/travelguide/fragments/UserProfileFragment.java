package com.example.travelguide.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.travelguide.R;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.adapter.ViewPageAdapter;
import com.example.travelguide.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;
import java.util.stream.Stream;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileFragment extends Fragment {

    private TextView nickName, name, userBioText, drawerBtn, tourRequest, firstPhotoConent, editProfile, bioText;
    private ImageButton userContentPhotoBtn, userContentMiddleBtn, userContentTourBtn, bioVisibleBtn, bioInvisibleBtn;
    private CircleImageView userPrfImage;
    private ImageView userBioTextState;
    private LinearLayout firstPhotoLinear, linearTour;
    private NestedScrollView nestedScrollView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private String loginType;
    private Context context;
    private boolean visible = true;
    private User user;
    private Bundle bundlePrfFrg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initTabLayout(View view) {


        TabLayout tabLayout = view.findViewById(R.id.tabs_profile);
        TabItem itemPhoto = view.findViewById(R.id.tab_photos_layout);
        TabItem itemLiked = view.findViewById(R.id.tab_liked_content_layout);
        TabItem itemTour = view.findViewById(R.id.tab_tours_layout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        assert getFragmentManager() != null;
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    private void onGetData() {
        if (checkUserData(getArguments())) {
            setUserData();
            if (getArguments() != null && getArguments().containsKey("user")) {
                bundlePrfFrg = new Bundle();
                user = (User) getArguments().getSerializable("user");
                bundlePrfFrg.putSerializable("user", user);
            }
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
                    .into(userPrfImage);
        }

    }

    private void initUI(View view) {
        name = view.findViewById(R.id.user_prf_name);
        drawerBtn = view.findViewById(R.id.drawer_button);
        nickName = view.findViewById(R.id.user_prf_nickName);
        userPrfImage = view.findViewById(R.id.user_prf_image);
        editProfile = view.findViewById(R.id.edit_profile);
        tourRequest = view.findViewById(R.id.tour_request_background);
        userContentPhotoBtn = view.findViewById(R.id.user_content_photo_fr);
        userContentMiddleBtn = view.findViewById(R.id.user_content_middle_fr);
        userContentTourBtn = view.findViewById(R.id.user_content_tour_fr);
        bioVisibleBtn = view.findViewById(R.id.bio_visible_btn);
        bioInvisibleBtn = view.findViewById(R.id.bio_invisible_btn);
        bioText = view.findViewById(R.id.bio_text);
        tabLayout = view.findViewById(R.id.tabs_profile);
        ((UserPageActivity) context)
                .loadFragment(new UserPhotoFragment(), null, R.id.on_user_prof_frg_container, false);
    }

    private void showBioText() {
        if (visible) {
            bioText.setVisibility(View.VISIBLE);
            visible = false;
        } else {
            bioText.setVisibility(View.GONE);
            visible = true;
        }
    }

    private void setClickListeners() {
        userContentPhotoBtn.setOnClickListener(this::onContentIconClick);
        userContentMiddleBtn.setOnClickListener(this::onContentIconClick);
        userContentTourBtn.setOnClickListener(this::onContentIconClick);
        bioVisibleBtn.setOnClickListener(this::onContentIconClick);
        bioInvisibleBtn.setOnClickListener(this::onContentIconClick);
        editProfile.setOnClickListener(v -> ((UserPageActivity)
                Objects.requireNonNull(getActivity()))
                .loadFragment(new UserEditFragment(), bundlePrfFrg, R.id.user_page_frg_container, true));
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
                logOut();
                break;
        }

        return true;
    };

    private void logOut() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Log out ?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (loginType != null) {
                        String currentLoginType = loginType;
                        switch (currentLoginType) {
                            case "facebook":
                                ((UserPageActivity) Objects.requireNonNull(getActivity())).signOutFromFbAccount();
                                break;
                            case "google":
                                ((UserPageActivity) Objects.requireNonNull(getActivity())).signOutFromGoogleAccount();
                                break;
                        }
                    } else {
                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }

    private void setBackground(View view, int drawable) {
        view.setBackground(getResources().getDrawable(drawable));
    }

    private void onContentIconClick(View v) {
        switch (v.getId()) {

            case R.id.user_content_photo_fr:
                setBackground(userContentPhotoBtn, R.drawable.profile_photos_pressed);
                setBackground(userContentTourBtn, R.drawable.profile_tour);
                setBackground(userContentMiddleBtn, R.drawable.profile_liked);
                ((UserPageActivity) context)
                        .loadFragment(new UserPhotoFragment(), null, R.id.on_user_prof_frg_container, false);
                break;

            case R.id.user_content_middle_fr:
                setBackground(userContentMiddleBtn, R.drawable.profile_liked_pressed);
                setBackground(userContentPhotoBtn, R.drawable.profile_photos);
                setBackground(userContentTourBtn, R.drawable.profile_tour);

                ((UserPageActivity) context)
                        .loadFragment(new UserContentFragment(), null, R.id.on_user_prof_frg_container, false);

                break;

            case R.id.user_content_tour_fr:
                setBackground(userContentTourBtn, R.drawable.profile_tour_pressed);
                setBackground(userContentPhotoBtn, R.drawable.profile_photos);
                setBackground(userContentMiddleBtn, R.drawable.profile_liked);

                ((UserPageActivity) context)
                        .loadFragment(new UserTourFragment(), null, R.id.on_user_prof_frg_container, false);

                break;

            case R.id.bio_visible_btn:
                bioText.setVisibility(View.VISIBLE);
                bioVisibleBtn.setVisibility(View.GONE);
                bioInvisibleBtn.setVisibility(View.VISIBLE);
                visible = false;
                break;

            case R.id.bio_invisible_btn:
                bioText.setVisibility(View.GONE);
                bioVisibleBtn.setVisibility(View.VISIBLE);
                bioInvisibleBtn.setVisibility(View.GONE);
                visible = true;
                break;
        }
    }
}
