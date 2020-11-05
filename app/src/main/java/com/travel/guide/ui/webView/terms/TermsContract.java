package com.travel.guide.ui.webView.terms;

import com.travel.guide.model.request.TermsPolicyRequest;
import com.travel.guide.model.response.TermsPolicyResponse;

public interface TermsContract {

    interface Presenter {

        void getTerms(TermsPolicyRequest termsPolicyRequest);

    }

    interface View {

        void onGetTerms(TermsPolicyResponse.Terms terms);

        void onGetError(String message);

    }

}
