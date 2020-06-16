package com.example.travelguide.activity;

import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.travelguide.R;
import com.example.travelguide.fragments.ForgotPswFragment;
import com.example.travelguide.fragments.RegisterFragment;
import com.example.travelguide.fragments.SignInFragment;
import com.example.travelguide.interfaces.FragmentClickActions;

public class SignInActivity extends AppCompatActivity implements FragmentClickActions {

    private SignInFragment signInFragment;
    private RegisterFragment registerFragment;
    private ForgotPswFragment forgotPswFragment;
    private Button fragmentHead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInFragment = new SignInFragment(this);
        registerFragment = new RegisterFragment(this);
        loadSignInFragment();
    }

//    public void iniUI() {
//        fragmentHead = findViewById(R.id.fragment_head);
//    }
//
//    public void checkCurrentFragment() {
//
//        SignInFragment checkSignFragment = (SignInFragment) getSupportFragmentManager().findFragmentByTag("signIn");
//        if (checkSignFragment != null && checkSignFragment.isVisible()) {
//            fragmentHead.setText(R.string.welcome);
//        }
//    }

    public void loadSignInFragment() {
//        fragmentHead.setText(R.string.welcome);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.animation_fragment_slide_down, R.anim.animation_fragment_slide_down)
                .add(R.id.fragment_container, signInFragment, "signIn")
                .commit();
    }

    public void loadSignUpFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.register_frg_container, registerFragment, "signUp")
                .addToBackStack(null)
                .commit();
    }

    public void loadForgotPswFragment() {
        forgotPswFragment = new ForgotPswFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.register_frg_container, forgotPswFragment, "forgotPswFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void registerBtnClicked() {
//        fragmentHead.setText(R.string.sign_up);
        loadSignUpFragment();
    }

    @Override
    public void backToSignInFragment() {

    }

}
