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

import travelguideapp.ge.travelguide.R;
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


public class HomePageActivity extends AppCompatActivity implements HomePageListener,
        ProfileFragment.OnPostChooseListener,
        ProfileFragment.ProfileFragmentCallBacks,
        HomeFragment.LoadCommentFragmentListener {

    private BottomNavigationView bottomNavigationView;
    private HomePagePresenter homePagePresenter;

    public HomePageActivity() {
        //Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        homePagePresenter = getHomePagePresenter();
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

    private HomePagePresenter getHomePagePresenter() {
        return new HomePagePresenter(this);
    }

    public void checkRequestType() {
        String changed = getIntent().getStringExtra("password_changed");
        if (changed != null)
            if (changed.equals("password_changed")) {
                HelperUI.loadFragment(ProfileFragment.getInstance(this), null, R.id.user_page_frg_container, false, true, this);
            }

        String option = getIntent().getStringExtra("option");
        if (option != null)
            if (option.equals("uploaded")) {
                HelperUI.loadFragment(ProfileFragment.getInstance(this), null, R.id.user_page_frg_container, false, true, this);
            }

        String requestFrom = getIntent().getStringExtra("request_from");
        if (requestFrom != null) {
            if (requestFrom.equals("customer_profile")) {
                if (getIntent().getBundleExtra("fragment_data") != null) {
                    Bundle fragmentData = getIntent().getBundleExtra("fragment_data");
                    HelperUI.loadFragment(HomeFragment.getInstance(this), fragmentData, R.id.user_page_frg_container, false, true, this);
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
                    Bundle data = new Bundle();
                    data.putSerializable("PostShowType", GetPostsFrom.FEED);
                    HelperUI.loadFragment(HomeFragment.getInstance(this), data, R.id.user_page_frg_container, false, true, HomePageActivity.this);
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
                    int reqCode = 1;
//                    notification();
//                    showNotification(this, "Please Wait", "Your post is uploading.", new Intent(), reqCode);
//                    HelperUI.loadFragment(new NotificationsFragment(), null, R.id.notification_fragment_container, true, true, HomePageActivity.this);
                    break;

                case R.id.bot_nav_profile:
                    HelperUI.loadFragment(ProfileFragment.getInstance(this), null, R.id.user_page_frg_container, false, true, HomePageActivity.this);
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
        ClientManager.googleSignInClient(this)
                .signOut()
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
                    Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
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
            Bundle data = new Bundle();
            data.putSerializable("PostShowType", GetPostsFrom.FEED);
            HelperUI.loadFragment(HomeFragment.getInstance(this), data, R.id.user_page_frg_container, false, true, HomePageActivity.this);
        }
    }

    public void loadCommentFragment(int storyId, int postId) {

        Bundle commentFragmentData = new Bundle();
        commentFragmentData.putInt("storyId", storyId);
        commentFragmentData.putInt("postId", postId);

//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//
//        int width = size.x / 3;
//        int height = size.y / 2;

//        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));

        HelperUI.loadFragment(new CommentFragment(), commentFragmentData, R.id.notification_fragment_container, true, true, this);

    }

    @Override
    public void onLoadCommentFragment(int postId, int storyId) {
        Bundle commentFragmentData = new Bundle();
        commentFragmentData.putInt("storyId", storyId);
        commentFragmentData.putInt("postId", postId);
        HelperUI.loadFragment(new CommentFragment(), commentFragmentData, R.id.notification_fragment_container, true, true, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!(bottomNavigationView.getSelectedItemId() == R.id.bot_nav_profile)) {
            bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
            Bundle data = new Bundle();
            data.putSerializable("PostShowType", GetPostsFrom.FEED);
            HelperUI.loadFragment(HomeFragment.getInstance(this), data, R.id.user_page_frg_container, false, true, HomePageActivity.this);
        }
    }

    @Override
    public void onPostChoose(Bundle fragmentData) {
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        HelperUI.loadFragment(HomeFragment.getInstance(this), fragmentData, R.id.user_page_frg_container, false, true, this);
    }

    public void onProfileChoose() {
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_profile);
        HelperUI.loadFragment(new ProfileFragment(), null, R.id.user_page_frg_container, true, false, this);
    }

    public void loadRepliesFragment(Bundle repliesFragmentData) {
        HelperUI.loadFragment(new RepliesFragment(), repliesFragmentData, R.id.notification_fragment_container, true, true, this);
    }

    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_uploading);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_uploading);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);

        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.main_icon_sun)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name = "Channel Name";// The user-visible name of the channel.
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }
        if (notificationManager != null) {
            notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id
        }
    }

    public void notification() {

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            mBuilder.setSmallIcon(R.drawable.main_icon_sun);
            mBuilder.setContentTitle("ttiele")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("dasdasd"))
                    .setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND)
                    .setLights(Color.WHITE, 500, 500)
                    .setContentText("sdasdasdassd");
        } else {
            RemoteViews customNotificationView = new RemoteViews(getPackageName(), R.layout.notification_uploading);
            customNotificationView.setTextViewText(R.id.text_view_collapsed_1, "dasdasd");
            customNotificationView.setTextViewText(R.id.text_view_collapsed_1, "dasdasd");
            mBuilder.setContent(customNotificationView);
            mBuilder.setSmallIcon(R.drawable.main_icon_sun);
            mBuilder.setAutoCancel(true);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setLights(Color.WHITE, 500, 500);
        }
// build notification
        mBuilder.setContentIntent(pendingIntent);
        if (mNotificationManager != null)
            mNotificationManager.notify(1000, mBuilder.build());
//        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_uploading);
//        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_uploading);
//
//        Intent clickIntent = new Intent(this, NotificationReceiver.class);
//        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent, 0);
//        collapsedView.setTextViewText(R.id.text_view_collapsed_1, "Hello World!");
//        collapsedView.setOnClickPendingIntent(R.id.text_view_collapsed_1, clickPendingIntent);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.main_icon_sun)
//                .setContentTitle("dasd")
//                .setContentText("asdasd")
//                .setCustomContentView(collapsedView)
//                .setCustomBigContentView(expandedView)
//                .setAutoCancel(true)
//                .setContentIntent(clickPendingIntent)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
////                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                .build();
//        NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (notificationManager != null)
//            notificationManager.notify(1, notification);
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
    public void onAuthError(String s) {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
