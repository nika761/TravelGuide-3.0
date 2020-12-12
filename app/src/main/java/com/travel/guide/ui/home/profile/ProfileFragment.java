package com.travel.guide.ui.home.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.travel.guide.R;
import com.travel.guide.helper.HelperMedia;
import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.ui.home.profile.changeLanguage.ChangeLangFragment;
import com.travel.guide.ui.home.profile.editProfile.ProfileEditActivity;
import com.travel.guide.ui.home.profile.favorites.FavoritePostFragment;
import com.travel.guide.ui.home.profile.posts.UserPostsFragment;
import com.travel.guide.ui.home.profile.tours.UserToursFragment;
import com.travel.guide.ui.home.profile.follow.FollowActivity;
import com.travel.guide.ui.home.HomePageActivity;
import com.travel.guide.utility.GlobalPreferences;
import com.travel.guide.helper.HelperUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travel.guide.enums.LoadWebViewBy.ABOUT;
import static com.travel.guide.enums.LoadWebViewBy.POLICY;
import static com.travel.guide.enums.LoadWebViewBy.TERMS;

public class ProfileFragment extends Fragment implements ProfileFragmentListener {

    private TextView nickName, name, bioBody, following, follower, reaction, bioHead;
    private ImageButton seeBio, hideBio;
    private CircleImageView userPrfImage;
    private ActionBarDrawerToggle toggle;
    private View mIncludedLayout;
    private FrameLayout loaderContainer;
    private LottieAnimationView loader;
    private LinearLayout bioContainer;

    private ProfileFragmentPresenter presenter;
    private String userName;
    private Context context;

    private ProfileResponse.Userinfo userInfo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        if (getActivity() != null) {
            try {
                Window window = getActivity().getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                window.setStatusBarColor(getResources().getColor(R.color.white, null));
                setSystemBarTheme(getActivity(), false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.setTitle("");
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
            toggle.syncState();

            toolbar.setNavigationOnClickListener(v -> {
                drawerLayout.addDrawerListener(toggle);
                drawerLayout.openDrawer(GravityCompat.START);
            });

            NavigationView navigationView = view.findViewById(R.id.burger_navigation_view);
            navigationView.setNavigationItemSelectedListener(navListener);
        }

        presenter = new ProfileFragmentPresenter(this);

        name = view.findViewById(R.id.user_prf_name_toolbar);

        loaderContainer = view.findViewById(R.id.profile_loader_container);
        loader = view.findViewById(R.id.profile_loader);

        mIncludedLayout = view.findViewById(R.id.profile_included_layout);
        ViewPager viewPager = mIncludedLayout.findViewById(R.id.profile_view_pager);
        TabLayout tabLayout = mIncludedLayout.findViewById(R.id.profile_tabs);

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(getChildFragmentManager());
        profilePagerAdapter.addFragment(new UserPostsFragment());
        profilePagerAdapter.addFragment(new FavoritePostFragment());
        profilePagerAdapter.addFragment(new UserToursFragment());
        viewPager.setAdapter(profilePagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.profile_photos);
        tabLayout.getTabAt(1).setIcon(R.drawable.profile_liked);
        tabLayout.getTabAt(2).setIcon(R.drawable.profile_tour);

        View followStates = mIncludedLayout.findViewById(R.id.profile_follow_states);
        followStates.setOnClickListener(this::onViewClick);

        TextView editProfile = mIncludedLayout.findViewById(R.id.profile_edit_profile_btn);
        editProfile.setOnClickListener(this::onViewClick);

        seeBio = mIncludedLayout.findViewById(R.id.profile_see_bio_btn);
        seeBio.setOnClickListener(this::onViewClick);

        hideBio = mIncludedLayout.findViewById(R.id.profile_hide_bio_btn);
        hideBio.setOnClickListener(this::onViewClick);

        userPrfImage = mIncludedLayout.findViewById(R.id.profile_image);
        nickName = mIncludedLayout.findViewById(R.id.user_prf_nickName);
        follower = mIncludedLayout.findViewById(R.id.followers_count);
        following = mIncludedLayout.findViewById(R.id.following_count);
        reaction = mIncludedLayout.findViewById(R.id.reactions_count);
        bioBody = mIncludedLayout.findViewById(R.id.bio_text);
        bioContainer = mIncludedLayout.findViewById(R.id.profile_bio);
        bioHead = mIncludedLayout.findViewById(R.id.profile_bio_head);


        return view;
    }

    private void setSystemBarTheme(final Activity activity, final boolean pIsDark) {

        final int lFlags = activity.getWindow().getDecorView().getSystemUiVisibility();

        activity.getWindow().getDecorView().setSystemUiVisibility(pIsDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoader(true);
        presenter.getProfile(GlobalPreferences.getAccessToken(context), new ProfileRequest(GlobalPreferences.getUserId(context)));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private NavigationView.OnNavigationItemSelectedListener navListener = item -> {

        switch (item.getItemId()) {
            case R.id.settings_share_profile:
                Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings_language:
                ChangeLangFragment changeLangFragment = new ChangeLangFragment();
                if (changeLangFragment.getDialog() != null) {
                    if (changeLangFragment.getDialog().getWindow() != null)
                        changeLangFragment.getDialog().getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                }
                changeLangFragment.show(getChildFragmentManager(), "fr");
                break;

            case R.id.settings_about:
                HelperUI.startWebActivity(context, ABOUT, "");
                break;

            case R.id.settings_privacy:
                HelperUI.startWebActivity(context, POLICY, "");
                break;

            case R.id.settings_terms:
                HelperUI.startWebActivity(context, TERMS, "");
                break;

            case R.id.settings_sing_out:
                logOutDialog();
                break;
        }
        return true;
    };

    private void logOutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sign out ?")
                .setPositiveButton("Yes", (dialog, which) -> ((HomePageActivity) context).onLogOutChoose())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_sign_out_dialog, null));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        }

        dialog.show();

    }

    private void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.profile_see_bio_btn:
                showBiography(true);
                break;

            case R.id.profile_hide_bio_btn:
                showBiography(false);
                break;

            case R.id.profile_follow_states:
                Intent intent = new Intent(context, FollowActivity.class);
                intent.putExtra("user_name", userName);
                startActivity(intent);
                break;

            case R.id.profile_edit_profile_btn:
                Intent editProfileIntent = new Intent(context, ProfileEditActivity.class);
                if (userInfo != null)
                    editProfileIntent.putExtra("user_info", userInfo);
                startActivity(editProfileIntent);
                try {
                    getActivity().overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onGetProfile(ProfileResponse.Userinfo userInfo) {
        this.userInfo = userInfo;

        HelperMedia.loadCirclePhoto(context, userInfo.getProfile_pic(), userPrfImage);

        mIncludedLayout.setVisibility(View.VISIBLE);

        userName = userInfo.getName() + " " + userInfo.getLastname();
        name.setText(String.format("%s %s", userInfo.getName(), userInfo.getLastname()));
        nickName.setText(String.format("@%s", userInfo.getNickname()));
        follower.setText(String.valueOf(userInfo.getFollower()));
        following.setText(String.valueOf(userInfo.getFollowing()));
        reaction.setText(String.valueOf(userInfo.getReactions()));
        setBiography(userInfo.getBiography());

//        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(bioHead.getContext());
//        Bundle params = new Bundle();
//        params.putString("user_profile_page", "this user is" + " " + userInfo.getName() + userInfo.getLastname());
//        firebaseAnalytics.logEvent("profile_page_event", params);

        getLoader(false);
    }

    @Override
    public void onGetError(String message) {
        getLoader(false);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void getLoader(boolean show) {

        if (show) {
            loaderContainer.setVisibility(View.VISIBLE);
            loader.setVisibility(View.VISIBLE);
        } else {
            loaderContainer.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getProfile(GlobalPreferences.getAccessToken(context), new ProfileRequest(GlobalPreferences.getUserId(context)));
    }

    private void showBiography(boolean show) {
        if (show) {
            bioBody.setVisibility(View.VISIBLE);
            hideBio.setVisibility(View.VISIBLE);
            seeBio.setVisibility(View.GONE);
        } else {
            bioBody.setVisibility(View.GONE);
            hideBio.setVisibility(View.GONE);
            seeBio.setVisibility(View.VISIBLE);
        }
    }

    private void setBiography(String biography) {
        if (biography != null && !biography.isEmpty()) {
            bioContainer.setVisibility(View.VISIBLE);
            seeBio.setVisibility(View.VISIBLE);
            bioHead.setVisibility(View.VISIBLE);
            bioBody.setVisibility(View.VISIBLE);
            bioBody.setText(biography);
        } else {
            bioContainer.setVisibility(View.GONE);
            bioHead.setVisibility(View.GONE);
            bioBody.setVisibility(View.GONE);
            seeBio.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }


    public interface OnPostChooseListener {
        void onPostChoose(Bundle fragmentData);
    }


}
