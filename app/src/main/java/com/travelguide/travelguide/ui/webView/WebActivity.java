package com.travelguide.travelguide.ui.webView;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travelguide.travelguide.R;
import com.travelguide.travelguide.helper.HelperUI;
import com.travelguide.travelguide.ui.webView.about.AboutFragment;
import com.travelguide.travelguide.ui.webView.policy.PolicyFragment;
import com.travelguide.travelguide.ui.webView.terms.TermsFragment;

import static com.travelguide.travelguide.helper.HelperUI.ABOUT;
import static com.travelguide.travelguide.helper.HelperUI.POLICY;
import static com.travelguide.travelguide.helper.HelperUI.TERMS;

public class WebActivity extends AppCompatActivity {

    private final static int WEB_FRAGMENT_CONTAINER = R.id.terms_policy_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);

        checkInputType();
    }

    private void checkInputType() {
        String type = getIntent().getStringExtra(HelperUI.TYPE);
        if (type != null) {
            switch (type) {
                case TERMS:
                    HelperUI.loadFragment(new TermsFragment(), null, WEB_FRAGMENT_CONTAINER, false, true, this);
                    break;

                case POLICY:
                    HelperUI.loadFragment(new PolicyFragment(), null, WEB_FRAGMENT_CONTAINER, false, true, this);
                    break;

                case ABOUT:
                    HelperUI.loadFragment(new AboutFragment(), null, WEB_FRAGMENT_CONTAINER, false, true, this);
                    break;
            }
        } else {
            Toast.makeText(this, "Type = null", Toast.LENGTH_SHORT).show();
        }
    }
}
