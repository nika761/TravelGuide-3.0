package travelguideapp.ge.travelguide.ui.webView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.base.BaseActivity;
import travelguideapp.ge.travelguide.enums.LoadWebViewBy;
import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.ui.webView.about.AboutFragment;
import travelguideapp.ge.travelguide.ui.webView.go.GoFragment;
import travelguideapp.ge.travelguide.ui.webView.policy.PolicyFragment;
import travelguideapp.ge.travelguide.ui.webView.terms.TermsFragment;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;

public class WebActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);
        checkRequestType();
//        setLanguage();
    }

    private void setLanguage() {
        Locale locale = new Locale(GlobalPreferences.getLanguage(this));
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void checkRequestType() {
        LoadWebViewBy loadWebViewType = (LoadWebViewBy) getIntent().getSerializableExtra(HelperUI.TYPE);
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
                    String url = getIntent().getStringExtra(HelperUI.GO_URL);
                    HelperUI.loadFragment(GoFragment.getInstance(url), null, R.id.terms_policy_fragment_container, false, true, this);
                    break;
            }
        } else {
            MyToaster.getUnknownErrorToast(this);
            finish();
        }
    }
}
