package com.travel.guide.ui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.travel.guide.R;
import com.travel.guide.helper.HelperClients;
import com.travel.guide.helper.HelperPref;
import com.travel.guide.ui.home.comments.CommentFragment;
import com.travel.guide.ui.home.comments.RepliesFragment;
import com.travel.guide.ui.home.home.HomeFragment;
import com.travel.guide.ui.home.profile.ProfileFragment;
import com.travel.guide.helper.HelperSystem;
import com.travel.guide.helper.HelperUI;
import com.travel.guide.ui.login.signIn.SignInActivity;
import com.travel.guide.ui.search.SearchActivity;
import com.travel.guide.ui.gallery.GalleryActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.travel.guide.enums.GetPostsFrom.FEED;
import static com.travel.guide.helper.HelperPref.FACEBOOK;
import static com.travel.guide.helper.HelperPref.GOOGLE;
import static com.travel.guide.helper.HelperPref.TRAVEL_GUIDE;
import static com.travel.guide.helper.HelperSystem.READ_EXTERNAL_STORAGE;

/**
 * Created by n.butskhrikidze on 01/07/2020.
 * <p>
 * დასაბამიდან იყო სიტყვა, და სიტყვა იყო ღმერთთან და ღმერთი იყო სიტყვა.
 * In the beginning was the Word, and the Word was with God, and the Word was God.
 * <p>
 */

public class HomePageActivity extends AppCompatActivity implements ProfileFragment.OnPostChooseListener {

    private GoogleSignInClient mGoogleSignInClient;
    private BottomNavigationView bottomNavigationView;
    public boolean commentFieldState;

    public HomePageActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        checkRequestType();
        initBtmNav();
    }

    public void checkRequestType() {
        String changed = getIntent().getStringExtra("password_changed");
        if (changed != null)
            if (changed.equals("password_changed")) {
                HelperUI.loadFragment(new ProfileFragment(), null, R.id.user_page_frg_container, false, true, this);
            }

        String option = getIntent().getStringExtra("option");
        if (option != null)
            if (option.equals("uploaded")) {
                HelperUI.loadFragment(new ProfileFragment(), null, R.id.user_page_frg_container, false, true, this);
            }

        String requestFrom = getIntent().getStringExtra("request_from");
        if (requestFrom != null) {
            if (requestFrom.equals("customer_profile")) {
                if (getIntent().getBundleExtra("fragment_data") != null) {
                    Bundle fragmentData = getIntent().getBundleExtra("fragment_data");
                    HelperUI.loadFragment(new HomeFragment(), fragmentData, R.id.user_page_frg_container, false, true, this);
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
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        bottomNavigationView.setItemIconSize(60);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bot_nav_home:
                    Bundle data = new Bundle();
                    data.putSerializable("PostShowType", FEED);
                    HelperUI.loadFragment(new HomeFragment(), data, R.id.user_page_frg_container, false, true, HomePageActivity.this);
                    break;

                case R.id.bot_nav_search:
                    Intent searchIntent = new Intent(HomePageActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    break;

                case R.id.bot_nav_add:

                    if (HelperSystem.isReadStoragePermission(HomePageActivity.this)) {
                        Intent galleryIntent = new Intent(HomePageActivity.this, GalleryActivity.class);
                        startActivity(galleryIntent);
                    } else
                        HelperSystem.requestReadStoragePermission(HomePageActivity.this);

                    break;

                case R.id.bot_nav_ntf:
                    int reqCode = 1;
                    showNotification(this, "Please Wait", "Your post is uploading.", new Intent(), reqCode);

//                    HelperUI.loadFragment(new NotificationsFragment(), null, R.id.notification_fragment_container, true, true, HomePageActivity.this);
                    break;

                case R.id.bot_nav_profile:
                    HelperUI.loadFragment(new ProfileFragment(), null, R.id.user_page_frg_container, false, true, HomePageActivity.this);
                    break;
            }

            return true;
        });


        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "with_testing");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
//        navViewPager = findViewById(R.id.user_page_view_pager);
//        navViewPagerAdapter = new BottomNavigationPageAdapter(getSupportFragmentManager());
    }

    public void logOutFromFacebook() {
        LoginManager.getInstance().logOut();
        onLogOutSuccess();
    }

    public void logOutFromGoogle() {
        HelperClients.googleSignInClient(this)
                .signOut()
                .addOnCompleteListener(this, task -> onLogOutSuccess())
                .addOnCanceledListener(this, () -> Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(this, e -> Toast.makeText(this, e.getMessage() + "Google Failed", Toast.LENGTH_SHORT).show());
    }

    public void onLogOutChoose() {
        String loginType = HelperPref.getLoginType(this);
        if (loginType == null) {
            Toast.makeText(this, "Please try again ", Toast.LENGTH_SHORT).show();
        } else {
            switch (loginType) {
                case FACEBOOK:
                    logOutFromFacebook();
                    break;

                case GOOGLE:
                    logOutFromGoogle();
                    break;

                case TRAVEL_GUIDE:
                    onLogOutSuccess();
                    break;
            }
        }
    }

    public void onLogOutSuccess() {

        HelperPref.removeAccessToken(this);
        HelperPref.removeLoginType(this);
        HelperPref.removeUserId(this);
        HelperPref.removeUserRole(this);

        Intent intent = new Intent(HomePageActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
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
        super.onBackPressed();
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
    protected void onStart() {
        super.onStart();
        if (!(bottomNavigationView.getSelectedItemId() == R.id.bot_nav_profile)) {
            bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
            Bundle data = new Bundle();
            data.putSerializable("PostShowType", FEED);
            HelperUI.loadFragment(new HomeFragment(), data, R.id.user_page_frg_container, false, true, HomePageActivity.this);
        }
    }

    @Override
    public void onPostChoose(Bundle fragmentData) {
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);

        HelperUI.loadFragment(new HomeFragment(), fragmentData, R.id.user_page_frg_container, false, true, this);
    }

    public void onProfileChoose() {
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_profile);
    }

    public void loadRepliesFragment(Bundle repliesFragmentData) {
        HelperUI.loadFragment(new RepliesFragment(), repliesFragmentData, R.id.notification_fragment_container, true, true, this);
    }

    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);

        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.main_icon_sun)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name = "Channel Name";// The user-visible name of the channel.
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        NotificationChannel mChannel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id
        }
    }
}
