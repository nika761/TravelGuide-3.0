package com.example.travelguide.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.R;
import com.example.travelguide.fragments.SignUpFragment;
import com.example.travelguide.utils.UtilsUI;

public class SignUpActivity extends AppCompatActivity {

    private static final int REGISTER_FRAGMENT_CONTAINER_ID = R.id.register_fragment_container_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        UtilsUI.loadFragment(new SignUpFragment(),null,REGISTER_FRAGMENT_CONTAINER_ID,false,this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}