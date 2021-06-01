package travelguideapp.ge.travelguide.ui.webView.policy;

import travelguideapp.ge.travelguide.base.BaseViewListener;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;

public interface PolicyListener extends BaseViewListener {
    void onGetPolicy(TermsPolicyResponse.Policy policy);
}
