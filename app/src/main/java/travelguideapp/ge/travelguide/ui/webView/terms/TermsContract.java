package travelguideapp.ge.travelguide.ui.webView.terms;

import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;

public interface TermsContract {

    interface Presenter {

        void getTerms(TermsPolicyRequest termsPolicyRequest);

    }

    interface View {

        void onGetTerms(TermsPolicyResponse.Terms terms);

        void onGetError(String message);

    }

}
