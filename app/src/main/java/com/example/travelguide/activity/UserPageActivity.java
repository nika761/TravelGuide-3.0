package com.example.travelguide.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.travelguide.R;
import com.example.travelguide.fragments.UserHomeFragment;
import com.example.travelguide.fragments.UserProfileFragment;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class UserPageActivity extends AppCompatActivity {

    private Button signOutBtn;
    private ImageView userPhotoLeft;
    private GoogleSignInClient mGoogleSignInClient;
    private Bundle bundlePrfFrg;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        initUI();
        ongGetUserDate();
        initBtmNav();
        initGoogleSignClient();
    }

    private void initUI() {
        userPhotoLeft = findViewById(R.id.user_photo_left);
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
        setUserProfileData(firstName, lastName, url, id, email, loginType);

    }

    private void initBtmNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.bot_nav_home);
        bottomNavigationView.setItemIconSize(60);
    }

    private void setUserProfileData(String firsName, String lastName, String url, String id, String email, String loginType) {
        bundlePrfFrg = new Bundle();
        bundlePrfFrg.putString("firstName", firsName);
        bundlePrfFrg.putString("lastName", lastName);
        bundlePrfFrg.putString("url", url);
        bundlePrfFrg.putString("id", id);
        bundlePrfFrg.putString("email", email);
        bundlePrfFrg.putString("loginType", loginType);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment;

        switch (item.getItemId()) {
            case R.id.bot_nav_home:
                selectedFragment = new UserHomeFragment();
                selectedFragment.setArguments(bundlePrfFrg);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.user_page_frg_container, selectedFragment)
                        .commit();
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
                selectedFragment.setArguments(bundlePrfFrg);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.user_page_frg_container, selectedFragment)
                        .addToBackStack(null)
                        .commit();
                break;
        }

        return true;
    };

    private void initGoogleSignClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(this), gso);
    }

    public void signOutFromGoogleAccount() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    if (user != null) {
                        Utils.deleteUser(this, user);
                    }
                    finish();
                });
    }
}
