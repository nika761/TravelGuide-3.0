package travelguideapp.ge.travelguide.ui.webView.policy;

import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;

public interface PolicyListener {
    void onGetPolicy(TermsPolicyResponse.Policy policy);

    void onGetError(String message);
}
