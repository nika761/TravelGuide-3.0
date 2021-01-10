package travelguideapp.ge.travelguide.ui.webView;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.enums.LoadWebViewBy;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.ui.webView.about.AboutFragment;
import travelguideapp.ge.travelguide.ui.webView.go.GoFragment;
import travelguideapp.ge.travelguide.ui.webView.policy.PolicyFragment;
import travelguideapp.ge.travelguide.ui.webView.terms.TermsFragment;

public class WebActivity extends AppCompatActivity {

    private final static int WEB_FRAGMENT_CONTAINER = R.id.terms_policy_fragment_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);

        checkRequestType();
    }

    private void checkRequestType() {
        LoadWebViewBy loadWebViewType = (LoadWebViewBy) getIntent().getSerializableExtra(HelperUI.TYPE);
        if (loadWebViewType != null) {
            switch (loadWebViewType) {
                case TERMS:
                    HelperUI.loadFragment(new TermsFragment(), null, WEB_FRAGMENT_CONTAINER, false, true, this);
                    break;

                case POLICY:
                    HelperUI.loadFragment(new PolicyFragment(), null, WEB_FRAGMENT_CONTAINER, false, true, this);
                    break;

                case ABOUT:
                    HelperUI.loadFragment(new AboutFragment(), null, WEB_FRAGMENT_CONTAINER, false, true, this);
                    break;

                case GO:
                    String url = getIntent().getStringExtra(HelperUI.GO_URL);
                    HelperUI.loadFragment(GoFragment.getInstance(url), null, WEB_FRAGMENT_CONTAINER, false, true, this);
                    break;
            }
        } else {
            MyToaster.getErrorToaster(this, "Try Again");
            finish();
        }
    }
}
