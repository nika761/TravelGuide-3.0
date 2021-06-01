package travelguideapp.ge.travelguide.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseFragment;
import travelguideapp.ge.travelguide.base.HomeParentActivity;
import travelguideapp.ge.travelguide.enums.WebViewType;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.HelperMedia;
import travelguideapp.ge.travelguide.listener.PostChooseListener;
import travelguideapp.ge.travelguide.model.parcelable.PostHomeParams;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.ui.profile.changeLanguage.ChangeLangFragment;
import travelguideapp.ge.travelguide.ui.profile.posts.PostPagerAdapter;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

import static travelguideapp.ge.travelguide.utility.LogUtils.LOG_E;
import static travelguideapp.ge.travelguide.utility.LogUtils.makeLogTag;

public class ProfileFragment extends BaseFragment<ProfileFragmentPresenter> implements ProfileFragmentListener {

    private static final String TAG = makeLogTag(ProfileFragment.class);

    public static ProfileFragment getInstance(ProfileFragmentCallBacks callBack) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.callBack = callBack;
        return profileFragment;
    }

    private static final int SETTINGS_ID_SHARE_PROFILE = R.id.settings_share_profile;
    private static final int SETTINGS_ID_LANGUAGE = R.id.settings_language;
    private static final int SETTINGS_ID_SIGN_OUT = R.id.settings_sing_out;
    private static final int SETTINGS_ID_PRIVACY = R.id.settings_privacy;
    private static final int SETTINGS_ID_ABOUT = R.id.settings_about;
    private static final int SETTINGS_ID_TERMS = R.id.settings_terms;

    private TextView followingCount;
    private TextView followerCount;
    private TextView reactionCount;
    private TextView nickName;
    private TextView bioHead;
    private TextView bioBody;
    private TextView name;

    private ImageButton seeBio;
    private ImageButton hideBio;

    private ActionBarDrawerToggle toggle;
    private CircleImageView userPrfImage;
    private LinearLayout bioContainer;

    private boolean isOnCreate = true;
    private String userName;
    private Context context;

    private ProfileResponse.Userinfo userProfileInfo;
    private ProfileFragmentCallBacks callBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
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

        name = view.findViewById(R.id.user_prf_name_toolbar);

        View mIncludedLayout = view.findViewById(R.id.profile_included_layout);
        ViewPager viewPager = mIncludedLayout.findViewById(R.id.profile_view_pager);
        TabLayout tabLayout = mIncludedLayout.findViewById(R.id.profile_tabs);

        PostPagerAdapter profilePagerAdapter = new PostPagerAdapter(getChildFragmentManager(), PostHomeParams.Type.MY_POSTS);
        profilePagerAdapter.setCallback((PostChooseListener) context);

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

        TextView editProfile = mIncludedLayout.findViewById(R.id.profile_edit_profile_btn);
        editProfile.setOnClickListener(v -> callBack.onChooseEditProfile(ProfileResponse.Userinfo.create(GlobalPreferences.getUserProfileInfo())));

        seeBio = mIncludedLayout.findViewById(R.id.profile_see_bio_btn);
        seeBio.setOnClickListener(v -> showBiography(true));

        hideBio = mIncludedLayout.findViewById(R.id.profile_hide_bio_btn);
        hideBio.setOnClickListener(v -> showBiography(false));

        userPrfImage = mIncludedLayout.findViewById(R.id.profile_image);
        nickName = mIncludedLayout.findViewById(R.id.user_prf_nickName);
        followerCount = mIncludedLayout.findViewById(R.id.followers_count);
        followingCount = mIncludedLayout.findViewById(R.id.following_count);
        reactionCount = mIncludedLayout.findViewById(R.id.reactions_count);
        bioBody = mIncludedLayout.findViewById(R.id.bio_text);
        bioContainer = mIncludedLayout.findViewById(R.id.profile_bio);
        bioHead = mIncludedLayout.findViewById(R.id.profile_bio_head);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachPresenter(ProfileFragmentPresenter.with(this));
        getProfileInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        attachPresenter(ProfileFragmentPresenter.with(this));
        changeStatusBarColor();
        updateProfileInfo();
    }

    private void changeStatusBarColor() {
        try {
            Window window = ((Activity) context).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.setStatusBarColor(getResources().getColor(R.color.white, null));
            final int flags = window.getDecorView().getSystemUiVisibility();
            window.getDecorView().setSystemUiVisibility(flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUserInfo(ProfileResponse.Userinfo userInfo) {
        GlobalPreferences.setUserProfileInfo(userInfo.serialize());
        this.userProfileInfo = userInfo;
    }

    private final NavigationView.OnNavigationItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case SETTINGS_ID_SHARE_PROFILE:
                try {
                    ((HomeParentActivity) context).shareContent(userProfileInfo.getShare_profile());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case SETTINGS_ID_LANGUAGE:
                ChangeLangFragment changeLangFragment = new ChangeLangFragment();
                if (changeLangFragment.getDialog() != null) {
                    if (changeLangFragment.getDialog().getWindow() != null)
                        changeLangFragment.getDialog().getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                }
                changeLangFragment.show(getChildFragmentManager(), "fr");
                break;

            case SETTINGS_ID_ABOUT:
                callBack.onChooseWebFlow(WebViewType.ABOUT, "");
                break;

            case SETTINGS_ID_PRIVACY:
                callBack.onChooseWebFlow(WebViewType.POLICY, "");
                break;

            case SETTINGS_ID_TERMS:
                callBack.onChooseWebFlow(WebViewType.TERMS, "");
                break;

            case SETTINGS_ID_SIGN_OUT:
                logOutDialog();
                break;
        }
        return true;
    };

    private void logOutDialog() {
        try {
            DialogManager.sureDialog(context, getString(R.string.sign_out), () -> callBack.onChooseLogOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onGetProfileInfo(ProfileResponse.Userinfo userInfo) {

        setUserInfo(userInfo);

        if (!isNullOrEmpty(userInfo.getProfile_pic())) {
            HelperMedia.loadCirclePhoto(context, userInfo.getProfile_pic(), userPrfImage);
        }

        if (!isNullOrEmpty(userInfo.getName()) && !isNullOrEmpty(userInfo.getLastname())) {
            userName = userInfo.getName() + " " + userInfo.getLastname();
            name.setText(String.format("%s %s", userInfo.getName(), userInfo.getLastname()));
        }

        if (!isNullOrEmpty(userInfo.getNickname()))
            nickName.setText(String.format("@%s", userInfo.getNickname()));

        if (!isNullOrEmpty(String.valueOf(userInfo.getFollower())))
            followerCount.setText(String.valueOf(userInfo.getFollower()));

        if (!isNullOrEmpty(String.valueOf(userInfo.getFollowing())))
            followingCount.setText(String.valueOf(userInfo.getFollowing()));

        if (!isNullOrEmpty(String.valueOf(userInfo.getReactions())))
            reactionCount.setText(String.valueOf(userInfo.getReactions()));

        setBiography(userInfo.getBiography());
    }


    private void showBiography(boolean show) {
        try {
            if (show) {
                bioBody.setVisibility(View.VISIBLE);
                hideBio.setVisibility(View.VISIBLE);
                seeBio.setVisibility(View.GONE);
            } else {
                bioBody.setVisibility(View.GONE);
                hideBio.setVisibility(View.GONE);
                seeBio.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBiography(String biography) {
        try {
            if (!isNullOrEmpty(biography)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getProfileInfo() {
        try {
            if (GlobalPreferences.getUserProfileInfo() != null && !GlobalPreferences.getUserProfileInfo().equals("")) {
                userProfileInfo = ProfileResponse.Userinfo.create(GlobalPreferences.getUserProfileInfo());
                onGetProfileInfo(userProfileInfo);
            } else {
                presenter.getProfile(new ProfileRequest(GlobalPreferences.getUserId()), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProfileInfo() {
        try {
            if (!isOnCreate) {
                if (presenter != null) {
                    presenter.getProfile(new ProfileRequest(GlobalPreferences.getUserId()), false);
                    LOG_E(TAG, "sended");
                }
            }
            isOnCreate = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ProfileFragmentCallBacks {

        void onChooseFollowers(String userName);

        void onChooseEditProfile(ProfileResponse.Userinfo userInfo);

        void onChooseWebFlow(WebViewType webViewType, String url);

        void onChooseLogOut();

    }


}
