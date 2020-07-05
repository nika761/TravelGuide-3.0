package com.example.travelguide.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.R;
import com.example.travelguide.fragments.ForgotPswFragment;
import com.example.travelguide.utils.UtilsUI;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final int FORGOT_PASS_FRAGMENT_CONTAINER_ID = R.id.forgot_password_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
//        loadFragment(new ForgotPswFragment(), null, FORGOT_PASS_FRAGMENT_CONTAINER_ID, false);
        UtilsUI.loadFragment(new ForgotPswFragment(),null,FORGOT_PASS_FRAGMENT_CONTAINER_ID,false,this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
