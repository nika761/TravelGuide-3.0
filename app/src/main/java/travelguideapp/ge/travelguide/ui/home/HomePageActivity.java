package travelguideapp.ge.travelguide.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.HomeParentActivity;
import travelguideapp.ge.travelguide.callback.OnPostChooseCallback;
import travelguideapp.ge.travelguide.enums.LoadWebViewBy;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.parcelable.PostDataLoad;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.ui.profile.editProfile.ProfileEditActivity;
import travelguideapp.ge.travelguide.ui.profile.follow.FollowActivity;
import travelguideapp.ge.travelguide.ui.webView.WebActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragment;
import travelguideapp.ge.travelguide.ui.profile.ProfileFragment;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;
import travelguideapp.ge.travelguide.ui.gallery.GalleryActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static travelguideapp.ge.travelguide.helper.HelperUI.GO_URL;
import static travelguideapp.ge.travelguide.helper.HelperUI.TYPE;

public class HomePageActivity extends HomeParentActivity implements HomePageListener, OnPostChooseCallback,
        ProfileFragment.ProfileFragmentCallBacks {

    private BottomNavigationView bottomNavigationView;
    private HomePagePresenter homePagePresenter;

    private boolean isFromLanguageChanged = true;
    private boolean backToProfile = false;

    public HomePageActivity() {
        //Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        homePagePresenter = HomePagePresenter.getInstance(this);
        getUserProfileInfo();
        initBtmNav();
        try {
            isFromLanguageChanged = getIntent().getBooleanExtra("IS_LANGUAGE_CHANGED", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void checkAppVersion() {
//        try {
//            if (BaseApplication.APP_VERSION > 0) {
//                int appVersion = BaseApplication.APP_VERSION;
//                if (GlobalPreferences.getAppVersion(this) < appVersion) {
//                    final String appPackageName = getPackageName();
//                    try {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                        finish();
//                    } catch (android.content.ActivityNotFoundException anfe) {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                        finish();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            finish();
//        }
//    }

    private void getUserProfileInfo() {
        try {
            homePagePresenter.getProfile(GlobalPreferences.getAccessToken(this), new ProfileRequest(GlobalPreferences.getUserId(this)));
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

    private void initBtmNav() {
        bottomNavigationView = findViewById(R.id.navigation);
//        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        bottomNavigationView.setItemIconSize(60);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.bot_nav_home:
                    try {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment_container);
                        if (!(fragment instanceof HomeFragment)) {
                            PostDataLoad postDataLoad = new PostDataLoad();
                            postDataLoad.setLoadSource(PostDataLoad.Source.FEED);
                            Bundle data = new Bundle();
                            data.putParcelable(PostDataLoad.INTENT_KEY_LOAD, postDataLoad);
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
                    if (SystemManager.isReadStoragePermission(HomePageActivity.this)) {
                        Intent galleryIntent = new Intent(HomePageActivity.this, GalleryActivity.class);
                        startActivity(galleryIntent);
                    } else
                        SystemManager.requestReadStoragePermission(HomePageActivity.this);
                    break;

                case R.id.bot_nav_ntf:
                    MyToaster.getToast(this, getString(R.string.no_notifications));
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SystemManager.READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(HomePageActivity.this, GalleryActivity.class);
                    startActivity(galleryIntent);
                } else {
                    MyToaster.getToast(this, "No permission granted");
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (backToProfile) {
            backToProfile = false;
            bottomNavigationView.setSelectedItemId(R.id.bot_nav_profile);
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
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        HelperUI.loadFragment(HomeFragment.getInstance(), fragmentData, R.id.home_fragment_container, false, true, this);
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
        overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
    }

    @Override
    public void onChooseEditProfile(ProfileResponse.Userinfo userInfo) {
        Intent profileIntent = new Intent(this, ProfileEditActivity.class);
        profileIntent.putExtra("user_info", userInfo);
        startActivity(profileIntent);
        overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
    }

    @Override
    public void onChooseWebFlow(LoadWebViewBy loadWebViewBy, String url) {
        Intent webIntent = new Intent(this, WebActivity.class);
        webIntent.putExtra(GO_URL, url);
        webIntent.putExtra(TYPE, loadWebViewBy);
        startActivity(webIntent);
    }

    @Override
    public void onChooseLogOut() {
        startLogOut();
    }

    @Override
    public void onGetProfile(ProfileResponse.Userinfo userInfo) {
        try {
            GlobalPreferences.saveUserProfileInfo(this, userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
        /*Supposedly TO-DO : Show Error Message  **/
    }

    @Override
    public void onAuthenticationError(String message) {
        super.onAuthenticateError(message);
    }

    @Override
    public void onConnectionError() {
        super.onConnectionError();
    }

    @Override
    protected void onDestroy() {
        if (homePagePresenter != null)
            homePagePresenter = null;
        super.onDestroy();
    }
}
