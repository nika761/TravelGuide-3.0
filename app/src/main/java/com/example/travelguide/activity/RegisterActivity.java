package com.example.travelguide.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.R;
import com.example.travelguide.fragments.RegisterFragment;

public class RegisterActivity extends AppCompatActivity {

    private static final int REGISTER_FRAGMENT_CONTAINER_ID = R.id.register_fragment_container_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadFragment(new RegisterFragment(), null, REGISTER_FRAGMENT_CONTAINER_ID, false);
    }

    public void loadFragment(Fragment currentFragment, Bundle data, int fragmentID, boolean backStack) {
        currentFragment.setArguments(data);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(fragmentID, currentFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
