package com.example.travelguide.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.R;
import com.example.travelguide.fragments.UserHomeFragment;
import com.example.travelguide.fragments.UserProfileFragment;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.Utils;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class UserPageActivity extends AppCompatActivity {

    private Button signOutBtn;
    private ImageView userPhotoLeft;
    private GoogleSignInClient mGoogleSignInClient;
    private Bundle bundlePrfFrg;
    private User user;
    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        initGoogleSignClient();
        initUI();
        ongGetUserDate();
        initBtmNav();
    }

    private void initUI() {
        userPhotoLeft = findViewById(R.id.user_photo_left);

    }

    public void hideBottomNavigation(Boolean visible) {
        if (visible)
            bottomNavigationView.setVisibility(View.VISIBLE);
        else
            bottomNavigationView.setVisibility(View.GONE);
    }

    private void ongGetUserDate() {
        String firstName = getIntent().getStringExtra("name");
        String lastName = getIntent().getStringExtra("lastName");
        String url = getIntent().getStringExtra("url");
        String id = getIntent().getStringExtra("id");
        String email = getIntent().getStringExtra("email");
        String loginType = getIntent().getStringExtra("loginType");
        user = new User(firstName, lastName, url, id, email, loginType);
        Utils.saveUser(this, user);
        setUserProfileData(user);
    }

    private void initBtmNav() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        bottomNavigationView.setItemIconSize(60);
    }

    private void setUserProfileData(User user) {
        bundlePrfFrg = new Bundle();
        bundlePrfFrg.putString("firstName", user.getName());
        bundlePrfFrg.putString("lastName", user.getLastName());
        bundlePrfFrg.putString("url", user.getUrl());
        bundlePrfFrg.putString("id", user.getId());
        bundlePrfFrg.putString("email", user.getEmail());
        bundlePrfFrg.putString("loginType", user.getLoginType());
        bundlePrfFrg.putSerializable("user", user);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        switch (item.getItemId()) {
            case R.id.bot_nav_home:
                selectedFragment = new UserHomeFragment();
                loadFragment(selectedFragment, bundlePrfFrg, R.id.user_page_frg_container, false);
                break;

            case R.id.bot_nav_search:
                Toast.makeText(this, "Search", Toast.LENGTH_LONG).show();
                break;

            case R.id.bot_nav_add:
                Toast.makeText(this, "Add", Toast.LENGTH_LONG).show();
                break;

            case R.id.bot_nav_ntf:
                Toast.makeText(this, "Notification", Toast.LENGTH_LONG).show();
                break;

            case R.id.bot_nav_profile:
                selectedFragment = new UserProfileFragment();
                loadFragment(selectedFragment, bundlePrfFrg, R.id.user_page_frg_container, false);
                break;
        }

        return true;
    };


    public void loadFragment(Fragment currentFragment, Bundle data, int fragmentID, boolean backStack) {
        currentFragment.setArguments(data);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();

        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(fragmentID, currentFragment).commit();
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
        user.setLoginStatus("no");
//        if (user != null) {
//            Utils.deleteUser(this, user);
//        }
        finish();
    }

    public void signOutFromGoogleAccount() {

        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            user.setLoginStatus("no");
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            if (user != null) {
                Utils.deleteUser(this, user);
            }
        }).addOnCanceledListener(this, () -> {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(this, e -> {
            e.printStackTrace();
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        });

        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
