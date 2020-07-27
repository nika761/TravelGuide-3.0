package com.example.travelguide.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.fragments.SignInFragment;
import com.example.travelguide.helper.HelperUI;

public class SignInActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUI();
//        loadFragment(new SignInFragment(), null, R.id.fragment_container, false);
        HelperUI.loadFragment(new SignInFragment(), null, R.id.fragment_container, false, this);
    }

    private void initUI() {
        animationView = findViewById(R.id.animation_view_sign);
        frameLayout = findViewById(R.id.frame_sign_loader);
    }

    public void setStatusBarColor() {
        setTheme(R.style.SecondAppTheme);
        Window window = getWindow();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(getResources().getColor(R.color.white));
    }

    public void startLoader() {
        frameLayout.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
    }

    public void stopLoader() {
        frameLayout.setVisibility(View.GONE);
        animationView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
