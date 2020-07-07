package com.example.travelguide.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelguide.R;
import com.example.travelguide.fragments.AboutFragment;
import com.example.travelguide.fragments.PolicyFragment;
import com.example.travelguide.fragments.TermsFragment;
import com.example.travelguide.utils.UtilsUI;

import static com.example.travelguide.utils.UtilsUI.ABOUT;
import static com.example.travelguide.utils.UtilsUI.POLICY;
import static com.example.travelguide.utils.UtilsUI.TERMS;

public class TermsAndPrivacyActivity extends AppCompatActivity {

    private final static int TERMS_POLICY_FRAGMENT_CONTAINER = R.id.terms_policy_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);
        checkCurrentFragment();
    }

    private void checkCurrentFragment() {
        String type = getIntent().getStringExtra(UtilsUI.TYPE);
        if (type != null) {
            switch (type) {
                case TERMS:
                    UtilsUI.loadFragment(new TermsFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER, false, this);
                    break;

                case POLICY:
                    UtilsUI.loadFragment(new PolicyFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER, false, this);
                    break;

                case ABOUT:
                    UtilsUI.loadFragment(new AboutFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER, false, this);
                    break;
            }
        }
    }
}
