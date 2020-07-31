package com.example.travelguide.ui.login.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.ui.profile.fragment.AboutFragment;
import com.example.travelguide.ui.login.fragment.PolicyFragment;
import com.example.travelguide.ui.login.fragment.TermsFragment;
import com.example.travelguide.helper.HelperUI;

import static com.example.travelguide.helper.HelperUI.ABOUT;
import static com.example.travelguide.helper.HelperUI.POLICY;
import static com.example.travelguide.helper.HelperUI.TERMS;

public class TermsAndPrivacyActivity extends AppCompatActivity {

    private final static int TERMS_POLICY_FRAGMENT_CONTAINER = R.id.terms_policy_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);
        checkCurrentFragment();
    }

    private void checkCurrentFragment() {
        String type = getIntent().getStringExtra(HelperUI.TYPE);
        if (type != null) {
            switch (type) {
                case TERMS:
                    HelperUI.loadFragment(new TermsFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER, false, this);
                    break;

                case POLICY:
                    HelperUI.loadFragment(new PolicyFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER, false, this);
                    break;

                case ABOUT:
                    HelperUI.loadFragment(new AboutFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER, false, this);
                    break;
            }
        }
    }
}
