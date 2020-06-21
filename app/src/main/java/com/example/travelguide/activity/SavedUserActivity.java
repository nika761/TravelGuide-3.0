package com.example.travelguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.travelguide.R;
import com.example.travelguide.fragments.ForgotPswFragment;
import com.example.travelguide.fragments.SavedUsersFragment;

public class SavedUserActivity extends AppCompatActivity {

    private SavedUsersFragment savedUsersFragment;
    private ForgotPswFragment forgotPswFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blackStatusBar));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_user);
        savedUsersFragment = new SavedUsersFragment();
        forgotPswFragment = new ForgotPswFragment();
        loadSaveUserFragment();
    }

    public void loadSaveUserFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.animation_fragment_slide_down, R.anim.animation_fragment_slide_down)
                .add(R.id.saved_user_fragment_container, savedUsersFragment, "savedUserFragment")
                .commit();
    }

    public void loadForgotPswFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.saved_user_fragment_container, forgotPswFragment, "forgotPswFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SavedUserActivity.this, ChooseLanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
