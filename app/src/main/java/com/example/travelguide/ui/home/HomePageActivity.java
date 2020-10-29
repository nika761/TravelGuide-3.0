package com.example.travelguide.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperClients;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.ui.home.comments.CommentFragment;
import com.example.travelguide.ui.home.home.HomeFragment;
import com.example.travelguide.ui.home.notifications.NotificationsFragment;
import com.example.travelguide.ui.home.profile.ProfileFragment;
import com.example.travelguide.model.response.LoginResponse;
import com.example.travelguide.helper.HelperSystem;
import com.example.travelguide.helper.HelperUI;
import com.example.travelguide.ui.search.SearchActivity;
import com.example.travelguide.ui.gallery.GalleryActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;

import static com.example.travelguide.helper.HelperSystem.READ_EXTERNAL_STORAGE;

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

        String changed = getIntent().getStringExtra("password_changed");
        String option = getIntent().getStringExtra("option");

        if (changed != null)
            if (changed.equals("password_changed"))
                HelperUI.loadFragment(new ProfileFragment(), null, R.id.user_page_frg_container, false, this);

        if (option != null)
            if (option.equals("uploaded"))
                HelperUI.loadFragment(new ProfileFragment(), null, R.id.user_page_frg_container, false, this);

        initBtmNav();
        initGoogleSignClient();
    }

    public void hideBottomNavigation(Boolean visible) {
        if (visible)
            bottomNavigationView.setVisibility(View.VISIBLE);
        else
            bottomNavigationView.setVisibility(View.GONE);
    }

    public void loadCommentFragment(int storyId, int postId) {

        Bundle commentFragmentData = new Bundle();
        commentFragmentData.putInt("storyId", storyId);
        commentFragmentData.putInt("postId", postId);

        HelperUI.loadFragment(new CommentFragment(), commentFragmentData, R.id.notification_fragment_container, true, this);
    }

    private void initBtmNav() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        bottomNavigationView.setItemIconSize(60);
//        navViewPager = findViewById(R.id.user_page_view_pager);
//        navViewPagerAdapter = new BottomNavigationPageAdapter(getSupportFragmentManager());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        switch (item.getItemId()) {
            case R.id.bot_nav_home:
                HelperUI.loadFragment(new HomeFragment(), null, R.id.user_page_frg_container, false, this);
                break;

            case R.id.bot_nav_search:
                Intent intent = new Intent(HomePageActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.bot_nav_add:
                if (HelperSystem.isReadStoragePermission(this)) {
                    Intent intent1 = new Intent(HomePageActivity.this, GalleryActivity.class);
                    startActivity(intent1);
                } else HelperSystem.requestReadStoragePermission(this);
                break;

            case R.id.bot_nav_ntf:
                HelperUI.loadFragment(new NotificationsFragment(), null, R.id.notification_fragment_container, true, this);
                break;

            case R.id.bot_nav_profile:
                HelperUI.loadFragment(new ProfileFragment(), null, R.id.user_page_frg_container, false, this);
                break;
        }
        return true;
    };

    public void setCommentFieldState(boolean commentFieldState) {
        this.commentFieldState = commentFieldState;
    }

    private void initGoogleSignClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(this), gso);
    }

    public void signOutFromFbAccount() {
        LoginManager.getInstance().logOut();
        HelperPref.saveAccessToken(this, null);
        HelperPref.saveCurrentUserId(this, 0);
        finish();
    }

    public void signOutFromAccount() {
        HelperPref.saveAccessToken(this, null);
        HelperPref.saveCurrentUserId(this, 0);
        finish();
    }

    public void signOutFromGoogleAccount() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            HelperPref.saveAccessToken(this, null);
            HelperPref.saveCurrentUserId(this, 0);})
                .addOnCanceledListener(this, () -> Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(this, e -> Toast.makeText(this, e.getMessage() + "Google Failed", Toast.LENGTH_SHORT).show());

        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent1 = new Intent(HomePageActivity.this, GalleryActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPostChoose(List<PostResponse.Posts> posts) {
        HelperUI.loadFragment(new HomeFragment(posts), null, R.id.user_page_frg_container, false, this);
    }

}
