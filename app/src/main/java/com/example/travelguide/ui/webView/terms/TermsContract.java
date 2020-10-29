package com.example.travelguide.ui.webView.terms;

import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.model.response.TermsPolicyResponse;

public interface TermsContract {

    interface Presenter {

        void getTerms(TermsPolicyRequest termsPolicyRequest);

    }

    interface View {

        void onGetTerms(TermsPolicyResponse.Terms terms);

        void onGetError(String message);

    }

}
