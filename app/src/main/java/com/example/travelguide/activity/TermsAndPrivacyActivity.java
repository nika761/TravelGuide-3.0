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
import com.example.travelguide.utils.UtilsTerms;

import static com.example.travelguide.utils.UtilsTerms.ABOUT;
import static com.example.travelguide.utils.UtilsTerms.POLICY;
import static com.example.travelguide.utils.UtilsTerms.TERMS;

public class TermsAndPrivacyActivity extends AppCompatActivity {

    private final static int TERMS_POLICY_FRAGMENT_CONTAINER_ID = R.id.terms_policy_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);
        checkCurrentFragment();
    }

    private void checkCurrentFragment() {
        String type = getIntent().getStringExtra(UtilsTerms.TYPE);
        if (type != null) {
            switch (type) {
                case TERMS:
                    loadFragment(new TermsFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER_ID, false);
                    break;

                case POLICY:
                    loadFragment(new PolicyFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER_ID, false);
                    break;

                case ABOUT:
                    loadFragment(new AboutFragment(), null, TERMS_POLICY_FRAGMENT_CONTAINER_ID, false);
                    break;
            }
        }
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

}
