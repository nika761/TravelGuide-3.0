package travelguideapp.ge.travelguide.ui.profile;

import android.app.Activity;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.HomeParentActivity;
import travelguideapp.ge.travelguide.callback.OnPostChooseCallback;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.ui.home.HomePageActivity;
import travelguideapp.ge.travelguide.ui.profile.changeLanguage.ChangeLangFragment;
import travelguideapp.ge.travelguide.ui.profile.favorites.FavoritePostFragment;
import travelguideapp.ge.travelguide.ui.profile.posts.UserPostsFragment;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import travelguideapp.ge.travelguide.enums.LoadWebViewBy;

public class ProfileFragment extends Fragment implements ProfileFragmentListener {

    public static ProfileFragment getInstance(ProfileFragmentCallBacks callBack) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.callBack = callBack;
        return profileFragment;
    }

    private TextView nickName, name, bioBody, followingCount, following, followerCount, follower, reactionCount, reaction, bioHead, editProfile;

    private String editProfileText, followingText, followersText, reactionsText, bioText, settingsText, shareProfileText,
            languageText, aboutText, termsText, privacyText, signOutText;

    private ImageButton seeBio, hideBio;
    private CircleImageView userPrfImage;
    private ActionBarDrawerToggle toggle;
    private FrameLayout loaderContainer;
    private LottieAnimationView loader;
    private LinearLayout bioContainer;
    private View mIncludedLayout;

    private boolean isOnCreate = true;
    private String userName;
    private Context context;

    private ProfileFragmentPresenter presenter;
    private ProfileResponse.Userinfo userInfo;
    private ProfileFragmentCallBacks callBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        try {
            Window window = ((Activity) context).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.setStatusBarColor(getResources().getColor(R.color.white, null));
            setSystemBarTheme(((Activity) context), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.addDrawerListener(toggle);
            drawerLayout.openDrawer(GravityCompat.START);
        });

        NavigationView navigationView = view.findViewById(R.id.burger_navigation_view);
        navigationView.setNavigationItemSelectedListener(navListener);

        presenter = new ProfileFragmentPresenter(this);

        name = view.findViewById(R.id.user_prf_name_toolbar);

        loaderContainer = view.findViewById(R.id.profile_loader_container);
        loader = view.findViewById(R.id.profile_loader);

        mIncludedLayout = view.findViewById(R.id.profile_included_layout);
        ViewPager viewPager = mIncludedLayout.findViewById(R.id.profile_view_pager);
        TabLayout tabLayout = mIncludedLayout.findViewById(R.id.profile_tabs);

        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(getChildFragmentManager());
        profilePagerAdapter.setCallback((OnPostChooseCallback) context);
        profilePagerAdapter.addFragment(new UserPostsFragment());
        profilePagerAdapter.addFragment(new FavoritePostFragment());
//        profilePagerAdapter.addFragment(new UserToursFragment());

        viewPager.setAdapter(profilePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        try {
            tabLayout.getTabAt(0).setIcon(R.drawable.profile_photos);
            tabLayout.getTabAt(1).setIcon(R.drawable.profile_liked);
//            tabLayout.getTabAt(2).setIcon(R.drawable.profile_tour);
        } catch (Exception e) {
            e.printStackTrace();
        }

        View followStates = mIncludedLayout.findViewById(R.id.profile_follow_states);
        followStates.setOnClickListener(v -> callBack.onChooseFollowers(userName));

        editProfile = mIncludedLayout.findViewById(R.id.profile_edit_profile_btn);
        editProfile.setOnClickListener(v -> callBack.onChooseEditProfile(GlobalPreferences.getUserProfileInfo(context)));

        seeBio = mIncludedLayout.findViewById(R.id.profile_see_bio_btn);
        seeBio.setOnClickListener(v -> showBiography(true));

        hideBio = mIncludedLayout.findViewById(R.id.profile_hide_bio_btn);
        hideBio.setOnClickListener(v -> showBiography(false));

        userPrfImage = mIncludedLayout.findViewById(R.id.profile_image);
        nickName = mIncludedLayout.findViewById(R.id.user_prf_nickName);
        followerCount = mIncludedLayout.findViewById(R.id.followers_count);
        follower = mIncludedLayout.findViewById(R.id.followers);
        followingCount = mIncludedLayout.findViewById(R.id.following_count);
        following = mIncludedLayout.findViewById(R.id.following);
        reactionCount = mIncludedLayout.findViewById(R.id.reactions_count);
        reaction = mIncludedLayout.findViewById(R.id.reactions);
        bioBody = mIncludedLayout.findViewById(R.id.bio_text);
        bioContainer = mIncludedLayout.findViewById(R.id.profile_bio);
        bioHead = mIncludedLayout.findViewById(R.id.profile_bio_head);
        return view;
    }

    private void setSystemBarTheme(final Activity activity, final boolean isDark) {
        final int lFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.getWindow().getDecorView().setSystemUiVisibility(isDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setStringsByLanguage();
        getProfileInfo();
    }

    private void getProfileInfo() {
        if (GlobalPreferences.getUserProfileInfo(context) != null) {
            this.userInfo = GlobalPreferences.getUserProfileInfo(context);
            onGetProfile(this.userInfo);
        } else {
            showLoader(true);
            presenter.getProfile(GlobalPreferences.getAccessToken(context), new ProfileRequest(GlobalPreferences.getUserId(context)));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private final NavigationView.OnNavigationItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.settings_share_profile:
                try {
                    ((HomeParentActivity) context).shareContent(userInfo.getShare_profile());
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                callBack.onChooseWebFlow(LoadWebViewBy.ABOUT, "");
                break;

            case R.id.settings_privacy:
                callBack.onChooseWebFlow(LoadWebViewBy.POLICY, "");
                break;

            case R.id.settings_terms:
                callBack.onChooseWebFlow(LoadWebViewBy.TERMS, "");
                break;

            case R.id.settings_sing_out:
                logOutDialog();
                break;
        }
        return true;
    };

    private void logOutDialog() {
        DialogManager.getAskingDialog(context, getString(R.string.sign_out), () -> callBack.onChooseLogOut());
    }

    @Override
    public void onGetProfile(ProfileResponse.Userinfo userInfo) {
        GlobalPreferences.saveUserProfileInfo(context, userInfo);

        this.userInfo = userInfo;

        HelperMedia.loadCirclePhoto(context, userInfo.getProfile_pic(), userPrfImage);

        mIncludedLayout.setVisibility(View.VISIBLE);

        userName = userInfo.getName() + " " + userInfo.getLastname();
        name.setText(String.format("%s %s", userInfo.getName(), userInfo.getLastname()));
        nickName.setText(String.format("@%s", userInfo.getNickname()));
        followerCount.setText(String.valueOf(userInfo.getFollower()));
        followingCount.setText(String.valueOf(userInfo.getFollowing()));
        reactionCount.setText(String.valueOf(userInfo.getReactions()));
        setBiography(userInfo.getBiography());
        showLoader(false);
    }

    @Override
    public void onAuthenticationError(String message) {
        try {
            ((HomePageActivity) context).onAuthenticationError(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionError() {
        try {
            ((HomePageActivity) context).onConnectionError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
        showLoader(false);
        MyToaster.getToast(context, message);
    }

    private void showLoader(boolean show) {
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
        if (!isOnCreate) {
            if (presenter != null)
                presenter.getProfile(GlobalPreferences.getAccessToken(context), new ProfileRequest(GlobalPreferences.getUserId(context)));
        }
        isOnCreate = false;
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
            hideBio.setVisibility(View.VISIBLE);
            bioHead.setVisibility(View.VISIBLE);
            bioBody.setVisibility(View.VISIBLE);
            bioBody.setText(biography);
        } else {
            bioContainer.setVisibility(View.GONE);
            bioHead.setVisibility(View.GONE);
            bioBody.setVisibility(View.GONE);
            seeBio.setVisibility(View.GONE);
            hideBio.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter = null;
        }
        super.onDestroy();
    }

    public interface ProfileFragmentCallBacks {

        void onChooseFollowers(String userName);

        void onChooseEditProfile(ProfileResponse.Userinfo userInfo);

        void onChooseWebFlow(LoadWebViewBy loadWebViewBy, String url);

        void onChooseLogOut();

    }


}
