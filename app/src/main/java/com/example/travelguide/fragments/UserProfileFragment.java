package com.example.travelguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.activity.UserFollowActivity;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.adapter.ViewPageAdapter;
import com.example.travelguide.interfaces.ILanguageActivity;
import com.example.travelguide.model.User;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.presenters.LanguagePresenter;
import com.example.travelguide.utils.UtilsTerms;
import com.example.travelguide.utils.UtilsUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.utils.UtilsPref.FACEBOOK;
import static com.example.travelguide.utils.UtilsPref.GOOGLE;

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
    private View followStates;
    private User user;
    private Bundle bundlePrfFrg;
    private LanguagePresenter languagePresenter;
    private LottieAnimationView lottieAnimationView;
    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        window.setStatusBarColor(getResources().getColor(R.color.white));

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
//        if (getArguments() != null && getArguments().containsKey("user")) {
//            user = (User) getArguments().getSerializable("user");
//            bundlePrfFrg = new Bundle();
//            bundlePrfFrg.putSerializable("user", user);
//            setUserData(user);
//        }

        if (getArguments() != null && getArguments().containsKey("user")) {
            LoginResponseModel.User serverUser = (LoginResponseModel.User) getArguments().getSerializable("user");
            if (serverUser != null)
                setUserData(serverUser);
        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUserData(LoginResponseModel.User user) {
//        name.setText(String.format("%s %s", user.getName(), user.getLastName()));
//        nickName.setText(String.format("@%s%s", user.getName(), user.getLastName()));
//        loginType = user.getLoginType();
//        if (user.getUrl() != null)
//            UtilsGlide.loadPhoto(context, user.getUrl(), userPrfImage);

        name.setText(String.format("%s %s", user.getName(), user.getLastname()));
        nickName.setText(String.format("@%s%s", user.getName(), user.getLastname()));

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
        followStates = view.findViewById(R.id.states);
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
        userContentPhotoBtn.setOnClickListener(this::onViewClick);
        userContentMiddleBtn.setOnClickListener(this::onViewClick);
        userContentTourBtn.setOnClickListener(this::onViewClick);
        bioVisibleBtn.setOnClickListener(this::onViewClick);
        bioInvisibleBtn.setOnClickListener(this::onViewClick);
        followStates.setOnClickListener(this::onViewClick);
        editProfile.setOnClickListener(this::onViewClick);
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

//            case R.id.settings_balance:
//                Toast.makeText(getContext(), "Balance", Toast.LENGTH_SHORT).show();
//                break;

            case R.id.settings_language:
                ChangeLangFragment f = new ChangeLangFragment();
                f.show(getChildFragmentManager(), "fr");
                break;

            case R.id.settings_about:
                UtilsTerms.startTermsAndPolicyActivity(context, UtilsTerms.ABOUT);
                break;

            case R.id.settings_privacy:
                UtilsTerms.startTermsAndPolicyActivity(context, UtilsTerms.POLICY);
                break;

            case R.id.settings_terms:
                UtilsTerms.startTermsAndPolicyActivity(context, UtilsTerms.TERMS);
                break;

            case R.id.settings_sing_out:
                logOut();
                break;
        }
        return true;
    };

//    private void initLanguageRecycler(List updatedLanguagesList) {
//        ChangeLangAdapter adapter = new ChangeLangAdapter(context);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//        adapter.setLanguageList(updatedLanguagesList);
//    }

    private void logOut() {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Sign out ?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (loginType != null) {
                        switch (loginType) {
                            case FACEBOOK:
                                ((UserPageActivity) Objects.requireNonNull(getActivity())).signOutFromFbAccount();
                                break;
                            case GOOGLE:
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

    private void onViewClick(View v) {
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

            case R.id.states:
                Intent intent = new Intent(context, UserFollowActivity.class);
                startActivity(intent);
                break;

            case R.id.edit_profile:
                ((UserPageActivity) context)
                        .loadFragment(new UserEditFragment(), bundlePrfFrg, R.id.user_page_frg_container, true);
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (languagePresenter != null) {
            languagePresenter = null;
        }
        super.onDestroy();
    }
}
