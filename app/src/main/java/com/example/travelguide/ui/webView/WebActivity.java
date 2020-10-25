package com.example.travelguide.ui.webView;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.R;
import com.example.travelguide.helper.HelperUI;
import com.example.travelguide.ui.webView.about.AboutFragment;
import com.example.travelguide.ui.webView.policy.PolicyFragment;
import com.example.travelguide.ui.webView.terms.TermsFragment;

import static com.example.travelguide.helper.HelperUI.ABOUT;
import static com.example.travelguide.helper.HelperUI.POLICY;
import static com.example.travelguide.helper.HelperUI.TERMS;

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
                    HelperUI.loadFragment(new TermsFragment(), null, WEB_FRAGMENT_CONTAINER, false, this);
                    break;

                case POLICY:
                    HelperUI.loadFragment(new PolicyFragment(), null, WEB_FRAGMENT_CONTAINER, false, this);
                    break;

                case ABOUT:
                    HelperUI.loadFragment(new AboutFragment(), null, WEB_FRAGMENT_CONTAINER, false, this);
                    break;
            }
        } else {
            Toast.makeText(this, "Type = null", Toast.LENGTH_SHORT).show();
        }
    }
}
