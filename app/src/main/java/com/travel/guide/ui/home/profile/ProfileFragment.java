package com.travel.guide.ui.home.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

import static com.travel.guide.enums.LoadWebViewType.ABOUT;
import static com.travel.guide.enums.LoadWebViewType.POLICY;
import static com.travel.guide.enums.LoadWebViewType.TERMS;
import static com.travel.guide.network.ApiEndPoint.ACCESS_TOKEN_BEARER;

public class ProfileFragment extends Fragment implements ProfileFragmentListener {

    private TextView nickName, name, bioBody, following, follower, reaction, bioHead;
    private ImageButton seeBio, hideBio;
    private LottieAnimationView loader;
    private CircleImageView userPrfImage;
    private ActionBarDrawerToggle toggle;
    private Context context;
    private View mIncludedLayout;
    private LinearLayout bioContainer;

    private ProfileFragmentPresenter presenter;
    private String userName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        loader = view.findViewById(R.id.loader_profile);
        name = view.findViewById(R.id.user_prf_name_toolbar);

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        loader.setVisibility(View.VISIBLE);
        presenter.getProfile(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(context), new ProfileRequest(GlobalPreferences.getUserId(context)));
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
                HelperUI.startWebActivity(context, ABOUT);
                break;

            case R.id.settings_privacy:
                HelperUI.startWebActivity(context, POLICY);
                break;

            case R.id.settings_terms:
                HelperUI.startWebActivity(context, TERMS);
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
                Intent editProfile = new Intent(context, ProfileEditActivity.class);
                startActivity(editProfile);
                break;
        }
    }

    @Override
    public void onGetProfile(ProfileResponse.Userinfo userInfo) {

        HelperMedia.loadCirclePhoto(context, userInfo.getProfile_pic(), userPrfImage);

        loader.setVisibility(View.GONE);
        mIncludedLayout.setVisibility(View.VISIBLE);

        userName = userInfo.getName() + " " + userInfo.getLastname();
        name.setText(String.format("%s %s", userInfo.getName(), userInfo.getLastname()));
        nickName.setText(String.format("@%s", userInfo.getNickname()));
        follower.setText(String.valueOf(userInfo.getFollower()));
        following.setText(String.valueOf(userInfo.getFollowing()));
        reaction.setText(String.valueOf(userInfo.getReactions()));
        setBiography(userInfo.getBiography());

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(bioHead.getContext());
        Bundle params = new Bundle();
        params.putString("user_profile_page", "this user is" + " " + userInfo.getName() + userInfo.getLastname());
        firebaseAnalytics.logEvent("profile_page_event", params);

    }

    @Override
    public void onGetError(String message) {
        loader.setVisibility(View.GONE);

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getProfile(ACCESS_TOKEN_BEARER + GlobalPreferences.getAccessToken(context), new ProfileRequest(GlobalPreferences.getUserId(context)));
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


    @Override
    public void onResume() {
        super.onResume();
//        try {
//            getView().requestFocus();
//            getView().setOnKeyListener((v, keyCode, event) -> {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//
//                    //your code
//
//                    return true;
//                }
//                return false;
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public interface OnPostChooseListener {
        void onPostChoose(Bundle fragmentData);
    }


}
