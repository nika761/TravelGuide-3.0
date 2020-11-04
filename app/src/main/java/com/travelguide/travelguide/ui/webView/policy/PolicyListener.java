package com.travelguide.travelguide.ui.webView.policy;

import com.travelguide.travelguide.model.response.TermsPolicyResponse;

public interface PolicyListener {
    void onGetPolicy(TermsPolicyResponse.Policy policy);

    void onGetError(String message);
}
