package travelguideapp.ge.travelguide.ui.webView.policy;

import travelguideapp.ge.travelguide.base.BaseListener;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;

public interface PolicyListener extends BaseListener {
    void onGetPolicy(TermsPolicyResponse.Policy policy);
}
