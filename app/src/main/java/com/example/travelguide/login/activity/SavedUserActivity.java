package com.example.travelguide.login.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.travelguide.R;
import com.example.travelguide.login.fragment.UsersSavedFragment;
import com.example.travelguide.helper.HelperUI;


public class SavedUserActivity extends AppCompatActivity {

    private static final int SAVED_USER_FRG_CONTAINER_ID = R.id.saved_user_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_user);
        HelperUI.loadFragment(new UsersSavedFragment(),null,SAVED_USER_FRG_CONTAINER_ID,false,this);
    }

    @Override
    public void onBackPressed() {
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
