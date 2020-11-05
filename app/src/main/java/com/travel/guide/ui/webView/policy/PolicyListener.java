package com.travel.guide.ui.webView.policy;

import com.travel.guide.model.response.TermsPolicyResponse;

public interface PolicyListener {
    void onGetPolicy(TermsPolicyResponse.Policy policy);

    void onGetError(String message);
}
