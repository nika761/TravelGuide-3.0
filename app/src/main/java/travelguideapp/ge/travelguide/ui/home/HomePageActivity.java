package travelguideapp.ge.travelguide.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BasePresenterActivity;
import travelguideapp.ge.travelguide.helper.AuthorizationManager;
import travelguideapp.ge.travelguide.listener.PostChooseListener;
import travelguideapp.ge.travelguide.enums.WebViewType;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.parcelable.HomePostParams;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.ui.profile.editProfile.ProfileEditActivity;
import travelguideapp.ge.travelguide.ui.profile.follow.FollowActivity;
import travelguideapp.ge.travelguide.ui.webView.WebActivity;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragment;
import travelguideapp.ge.travelguide.ui.profile.ProfileFragment;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;
import travelguideapp.ge.travelguide.ui.gallery.GalleryActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends BasePresenterActivity<HomePagePresenter> implements HomePageListener, PostChooseListener,
        ProfileFragment.ProfileFragmentCallBacks {

    public static Intent getRedirectIntent(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private BottomNavigationView bottomNavigationView;

    private boolean isFromLanguageChanged = true;
    private boolean backToProfile = false;

    public HomePageActivity() {
        //Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        attachPresenter(HomePagePresenter.with(this));
        getUserProfileInfo();
        initBtmNav();
        try {
            isFromLanguageChanged = getIntent().getBooleanExtra("IS_LANGUAGE_CHANGED", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserProfileInfo() {
        try {
            presenter.getProfile(new ProfileRequest(GlobalPreferences.getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkRequestType() {
        String changed = getIntent().getStringExtra("password_changed");
        if (changed != null)
            if (changed.equals("password_changed")) {
                HelperUI.loadFragment(ProfileFragment.getInstance(this), null, R.id.home_fragment_container, false, true, this);
            }

        String option = getIntent().getStringExtra("option");
        if (option != null)
            if (option.equals("uploaded")) {
                HelperUI.loadFragment(ProfileFragment.getInstance(this), null, R.id.home_fragment_container, false, true, this);
            }

        String requestFrom = getIntent().getStringExtra("request_from");
        if (requestFrom != null) {
            if (requestFrom.equals("customer_profile")) {
                if (getIntent().getBundleExtra("fragment_data") != null) {
                    Bundle fragmentData = getIntent().getBundleExtra("fragment_data");
                    HelperUI.loadFragment(HomeFragment.getInstance(), fragmentData, R.id.home_fragment_container, false, true, this);
                }
            }
        }

    }

    public void hideBottomNavigation(Boolean visible) {
        try {
            if (visible)
                bottomNavigationView.setVisibility(View.VISIBLE);
            else
                bottomNavigationView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(HomePageActivity.this, GalleryActivity.class);
        startActivity(galleryIntent);
    }

    private void initBtmNav() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconSize(60);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.bot_nav_home:
                    try {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment_container);
                        if (!(fragment instanceof HomeFragment) || backToProfile) {
                            backToProfile = false;
                            HomePostParams postDataLoad = new HomePostParams();
                            postDataLoad.setPageType(HomePostParams.Type.FEED);
                            Bundle data = new Bundle();
                            data.putParcelable(HomePostParams.POST_HOME_PARAMS, postDataLoad);
                            HelperUI.loadFragment(HomeFragment.getInstance(), data, R.id.home_fragment_container, false, true, HomePageActivity.this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.bot_nav_search:
                    Intent searchIntent = new Intent(HomePageActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    break;

                case R.id.bot_nav_add:
                    if (isPermissionGranted(READ_EXTERNAL_STORAGE)) {
                        openGallery();
                    } else {
                        requestPermission(READ_EXTERNAL_STORAGE);
                    }
                    break;

                case R.id.bot_nav_ntf:
                    MyToaster.showToast(this, getString(R.string.no_notifications));
                    break;

                case R.id.bot_nav_profile:
                    HelperUI.loadFragment(ProfileFragment.getInstance(this), null, R.id.home_fragment_container, false, true, HomePageActivity.this);
                    break;
            }

            return true;
        });


//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.METHOD, "with_testing");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    @Override
    public void onPermissionResult(boolean permissionGranted) {
        if (permissionGranted) {
            openGallery();
        } else {
            MyToaster.showToast(this, "No permission");
        }
    }

    @Override
    public void onBackPressed() {
        if (backToProfile) {
            FragmentManager homeFragmentChildManager = getCurrentChildFragmentManager();
            if (homeFragmentChildManager != null) {
                if (homeFragmentChildManager.getBackStackEntryCount() == 0) {
                    backToProfile = false;
                    super.onBackPressed();
//                    bottomNavigationView.setSelectedItemId(R.id.bot_nav_profile);
                } else {
                    homeFragmentChildManager.popBackStackImmediate();
                }
            }
        } else {
            if (bottomNavigationView.getSelectedItemId() == R.id.bot_nav_home) {
                super.onBackPressed();
            } else {
                bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFromLanguageChanged) {
            bottomNavigationView.setSelectedItemId(R.id.bot_nav_profile);
            isFromLanguageChanged = false;
        } else {
            if (!(bottomNavigationView.getSelectedItemId() == R.id.bot_nav_profile)) {
                bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
            }
        }
    }

    @Override
    public void onPostChoose(Bundle fragmentData) {

        try {
            backToProfile = fragmentData.getBoolean("back_to_profile");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (backToProfile) {
            HelperUI.loadFragment(HomeFragment.getInstance(), fragmentData, R.id.home_fragment_container, true, false, this);
        } else {
            HelperUI.loadFragment(HomeFragment.getInstance(), fragmentData, R.id.home_fragment_container, false, true, this);
        }

    }

    public void onProfileChoose() {
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_profile);
//        HelperUI.loadFragment(ProfileFragment.getInstance(this), null, R.id.home_fragment_container, true, false, this);
    }

    @Override
    public void onChooseFollowers(String userName) {
        Intent followIntent = new Intent(this, FollowActivity.class);
        followIntent.putExtra("user_name", userName);
        startActivity(followIntent);
//        overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
    }

    @Override
    public void onChooseEditProfile(ProfileResponse.Userinfo userInfo) {
        Intent profileIntent = new Intent(this, ProfileEditActivity.class);
        profileIntent.putExtra("user_info", userInfo);
        startActivity(profileIntent);
        overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
    }

    @Override
    public void onChooseWebFlow(WebViewType webViewType, String url) {
        startActivity(WebActivity.getWebViewIntent(this, webViewType, url));
    }

    @Override
    public void onChooseLogOut() {
        AuthorizationManager.logOut(this);
    }

    @Override
    public void onGetProfile(ProfileResponse.Userinfo userInfo) {
        try {
            GlobalPreferences.setUserProfileInfo(userInfo.serialize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
