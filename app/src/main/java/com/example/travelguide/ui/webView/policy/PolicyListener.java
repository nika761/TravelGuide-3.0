package com.example.travelguide.ui.webView.policy;

import com.example.travelguide.model.response.TermsPolicyResponse;

public interface PolicyListener {
    void onGetPolicy(TermsPolicyResponse.Policy policy);

    void onGetError(String message);
}
