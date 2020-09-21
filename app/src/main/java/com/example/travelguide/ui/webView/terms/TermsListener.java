package com.example.travelguide.ui.webView.terms;

import com.example.travelguide.model.response.TermsPolicyResponse;

public interface TermsListener {
    void onGetTermsResult(TermsPolicyResponse termsPolicyResponse);
}
