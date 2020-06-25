package com.example.travelguide.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.R;
import com.example.travelguide.fragments.ForgotPswFragment;
import com.example.travelguide.fragments.UsersSavedFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

import java.util.Objects;

public class SavedUserActivity extends AppCompatActivity {

    private static final int SAVED_USER_FRG_CONTAINER_ID = R.id.saved_user_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_user);

        loadFragment(new UsersSavedFragment(), null, SAVED_USER_FRG_CONTAINER_ID, false);

    }

    public void loadFragment(Fragment currentFragment, Bundle data, int fragmentID, boolean backStack) {
        currentFragment.setArguments(data);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.add(fragmentID, currentFragment).commit();
    }


    @Override
    public void onBackPressed() {
        setStatusBarColor();
        super.onBackPressed();
//        Intent intent = new Intent(SavedUserActivity.this, SignInActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }


//    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
//            updateWithToken(newAccessToken);
//        }
//    };
//
//    private void updateWithToken(AccessToken currentAccessToken) {
//
//        if (currentAccessToken != null) {
//
//        } else {
//
//        }
//    }

    private void setStatusBarColor() {
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blackStatusBar));
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

}
