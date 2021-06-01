package travelguideapp.ge.travelguide.ui.webView.policy;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class PolicyPresenter extends BasePresenter<PolicyListener> {

    private final ApiService apiService;

    private PolicyPresenter(PolicyListener policyListener) {
        super.attachView(policyListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static PolicyPresenter with(PolicyListener policyListener) {
        return new PolicyPresenter(policyListener);
    }

    void getPolicy(TermsPolicyRequest termsPolicyRequest) {
        super.showLoader();
        apiService.getTerms(termsPolicyRequest).enqueue(new BaseCallback<TermsPolicyResponse>(this) {
            @Override
            protected void onSuccess(Response<TermsPolicyResponse> response) {
                if (isViewAttached()) {
                    try {
                        listener.onGetPolicy(response.body().getPolicy());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
