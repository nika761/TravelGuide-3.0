package com.example.travelguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.activity.UserFollowActivity;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.adapter.pager.ProfilePagerAdapter;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.response.LoginResponseModel;
import com.example.travelguide.presenters.LanguagePresenter;
import com.example.travelguide.helper.HelperUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.helper.HelperPref.FACEBOOK;
import static com.example.travelguide.helper.HelperPref.GOOGLE;
import static com.example.travelguide.helper.HelperUI.ABOUT;
import static com.example.travelguide.helper.HelperUI.POLICY;
import static com.example.travelguide.helper.HelperUI.TERMS;

public class UserProfileFragment extends Fragment {

    private TextView nickName, name, userBioText, drawerBtn, tourRequest, firstPhotoConent, editProfile, bioText;
    private ImageButton bioVisibleBtn, bioInvisibleBtn;
    private CircleImageView userPrfImage;
    private ImageView userBioTextState;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String loginType;
    private Context context;
    private View followStates;
    private Bundle bundlePrfFrg;
    private LanguagePresenter languagePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        toolbar.setTitle("");
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.addDrawerListener(toggle);
            drawerLayout.openDrawer(GravityCompat.START);
        });

        NavigationView navigationView = view.findViewById(R.id.burger_navigation_view);
        navigationView.setNavigationItemSelectedListener(navListener);

        View myLayout = view.findViewById(R.id.test);
        viewPager = myLayout.findViewById(R.id.viewpager_test);
        tabLayout = myLayout.findViewById(R.id.tabs_test);

        bioText = myLayout.findViewById(R.id.bio_text);

        followStates = myLayout.findViewById(R.id.states_test);
        followStates.setOnClickListener(this::onViewClick);

        editProfile = myLayout.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(this::onViewClick);

        bioVisibleBtn = myLayout.findViewById(R.id.bio_visible_btn);
        bioVisibleBtn.setOnClickListener(this::onViewClick);

        bioInvisibleBtn = myLayout.findViewById(R.id.bio_invisible_btn);
        bioInvisibleBtn.setOnClickListener(this::onViewClick);

        userPrfImage = myLayout.findViewById(R.id.user_prf_image);
        name = view.findViewById(R.id.user_prf_name_toolbar);
        nickName = myLayout.findViewById(R.id.user_prf_nickName);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabWithViewPager(viewPager);
        setUserData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setUserData() {

        if (HelperPref.getCurrentAccessToken(context) != null) {
            List<LoginResponseModel.User> loggedUser = HelperPref.getServerSavedUsers(context);
            name.setText(String.format("%s %s", loggedUser.get(0).getName(), loggedUser.get(0).getLastname()));
            nickName.setText(loggedUser.get(0).getNickname());

        } else {
            Toast.makeText(getContext(), "Data Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupTabWithViewPager(ViewPager viewPager) {
        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(getActivity().getSupportFragmentManager());
        profilePagerAdapter.addFragment(new UserPhotoFragment());
        profilePagerAdapter.addFragment(new UserContentFragment());
        profilePagerAdapter.addFragment(new UserTourFragment());
        viewPager.setAdapter(profilePagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.profile_photos);
        tabLayout.getTabAt(1).setIcon(R.drawable.profile_liked);
        tabLayout.getTabAt(2).setIcon(R.drawable.profile_tour);
    }


    private NavigationView.OnNavigationItemSelectedListener navListener = item -> {

        switch (item.getItemId()) {
            case R.id.settings_share_profile:
                Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_language:
                ChangeLangFragment f = new ChangeLangFragment();
                f.show(getChildFragmentManager(), "fr");
                break;

            case R.id.settings_about:
                HelperUI.startTermsAndPolicyActivity(context, ABOUT);
                break;

            case R.id.settings_privacy:
                HelperUI.startTermsAndPolicyActivity(context, POLICY);
                break;

            case R.id.settings_terms:
                HelperUI.startTermsAndPolicyActivity(context, TERMS);
                break;

            case R.id.settings_sing_out:
                logOut();
                break;
        }
        return true;
    };

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

    private void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.bio_visible_btn:
                bioText.setVisibility(View.VISIBLE);
                bioVisibleBtn.setVisibility(View.GONE);
                bioInvisibleBtn.setVisibility(View.VISIBLE);
                break;

            case R.id.bio_invisible_btn:
                bioText.setVisibility(View.GONE);
                bioVisibleBtn.setVisibility(View.VISIBLE);
                bioInvisibleBtn.setVisibility(View.GONE);
                break;

            case R.id.states_test:
                Intent intent = new Intent(context, UserFollowActivity.class);
                startActivity(intent);
                break;

            case R.id.edit_profile:
                HelperUI.loadFragment(new UserEditFragment(), null, R.id.user_page_frg_container, true, ((UserPageActivity) context));
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
