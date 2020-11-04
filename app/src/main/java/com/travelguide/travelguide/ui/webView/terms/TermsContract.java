package com.travelguide.travelguide.ui.webView.terms;

import com.travelguide.travelguide.model.request.TermsPolicyRequest;
import com.travelguide.travelguide.model.response.TermsPolicyResponse;

public interface TermsContract {

    interface Presenter {

        void getTerms(TermsPolicyRequest termsPolicyRequest);

    }

    interface View {

        void onGetTerms(TermsPolicyResponse.Terms terms);

        void onGetError(String message);

    }

}
