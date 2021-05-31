package travelguideapp.ge.travelguide.ui.webView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.enums.WebViewType;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.ui.webView.about.AboutFragment;
import travelguideapp.ge.travelguide.ui.webView.go.GoFragment;
import travelguideapp.ge.travelguide.ui.webView.policy.PolicyFragment;
import travelguideapp.ge.travelguide.ui.webView.terms.TermsFragment;

public class WebActivity extends BaseActivity {

    private static final String WEB_VIEW_TYPE = "type";
    private static final String WEB_VIEW_URL = "url";

    public static Intent getWebViewIntent(Context context, WebViewType webViewType, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WEB_VIEW_TYPE, webViewType);
        intent.putExtra(WEB_VIEW_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);
        checkRequestType();
    }

    private void checkRequestType() {
        WebViewType loadWebViewType = (WebViewType) getIntent().getSerializableExtra(WEB_VIEW_TYPE);
        if (loadWebViewType != null) {
            switch (loadWebViewType) {
                case TERMS:
                    HelperUI.loadFragment(new TermsFragment(), null, R.id.terms_policy_fragment_container, false, true, this);
                    break;

                case POLICY:
                    HelperUI.loadFragment(new PolicyFragment(), null, R.id.terms_policy_fragment_container, false, true, this);
                    break;

                case ABOUT:
                    HelperUI.loadFragment(new AboutFragment(), null, R.id.terms_policy_fragment_container, false, true, this);
                    break;

                case GO:
                    String url = getIntent().getStringExtra(WEB_VIEW_URL);
                    HelperUI.loadFragment(GoFragment.getInstance(url), null, R.id.terms_policy_fragment_container, false, true, this);
                    break;
            }
        } else {
            MyToaster.showUnknownErrorToast(this);
            finish();
        }
    }
}
