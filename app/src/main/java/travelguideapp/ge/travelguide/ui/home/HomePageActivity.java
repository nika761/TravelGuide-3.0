package travelguideapp.ge.travelguide.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.callback.OnPostChooseCallback;
import travelguideapp.ge.travelguide.helper.ClientManager;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.ui.home.profile.editProfile.ProfileEditActivity;
import travelguideapp.ge.travelguide.ui.home.profile.follow.FollowActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.ui.home.comments.CommentFragment;
import travelguideapp.ge.travelguide.ui.home.comments.RepliesFragment;
import travelguideapp.ge.travelguide.ui.home.home.HomeFragment;
import travelguideapp.ge.travelguide.ui.home.profile.ProfileFragment;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;
import travelguideapp.ge.travelguide.ui.search.SearchActivity;
import travelguideapp.ge.travelguide.ui.gallery.GalleryActivity;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import travelguideapp.ge.travelguide.enums.GetPostsFrom;


public class HomePageActivity extends BaseActivity implements HomePageListener, OnPostChooseCallback,
        ProfileFragment.ProfileFragmentCallBacks {

    private BottomNavigationView bottomNavigationView;
    private HomePagePresenter homePagePresenter;

    private boolean isFirstTime = true;

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
    }

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
        if (visible)
            bottomNavigationView.setVisibility(View.VISIBLE);
        else
            bottomNavigationView.setVisibility(View.GONE);
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
                            Bundle data = new Bundle();
                            data.putSerializable("PostShowType", GetPostsFrom.FEED);
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

    public void logOutFromFacebook() {
        try {
            LoginManager.getInstance().logOut();
            onLogOutSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOutFromGoogle() {
        ClientManager.googleSignInClient(this).signOut()
                .addOnCompleteListener(this, task -> onLogOutSuccess())
                .addOnCanceledListener(this, () -> MyToaster.getErrorToaster(this, "Canceled"))
                .addOnFailureListener(this, e -> MyToaster.getErrorToaster(this, e.getMessage() + "Google Failed"));
    }

    public void onLogOutSuccess() {
        GlobalPreferences.removeAccessToken(this);
        GlobalPreferences.removeLoginType(this);
        GlobalPreferences.removeUserId(this);
        GlobalPreferences.removeUserRole(this);

        Intent intent = new Intent(HomePageActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
                    MyToaster.getErrorToaster(this, "No permission granted");
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.bot_nav_home) {
            super.onBackPressed();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (isFirstTime) {
        if (!(bottomNavigationView.getSelectedItemId() == R.id.bot_nav_profile)) {
            bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        }
//            isFirstTime = false;
//        }
    }

    @Override
    public void onPostChoose(Bundle fragmentData) {
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        HelperUI.loadFragment(HomeFragment.getInstance(), fragmentData, R.id.home_fragment_container, false, true, this);
    }

    public void onProfileChoose() {
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_profile);
        HelperUI.loadFragment(new ProfileFragment(), null, R.id.home_fragment_container, true, false, this);
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
    public void onChooseLogOut() {
        try {
            String loginType = GlobalPreferences.getLoginType(this);

            switch (loginType) {
                case GlobalPreferences.FACEBOOK:
                    logOutFromFacebook();
                    break;

                case GlobalPreferences.GOOGLE:
                    logOutFromGoogle();
                    break;

                case GlobalPreferences.TRAVEL_GUIDE:
                    onLogOutSuccess();
                    break;

            }
        } catch (Exception e) {
            MyToaster.getUnknownErrorToast(this);
            e.printStackTrace();
        }

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
        /*Supposedly TO-DO : Show Error Message  **/
    }

}
