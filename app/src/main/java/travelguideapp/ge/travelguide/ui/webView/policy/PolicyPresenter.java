package travelguideapp.ge.travelguide.ui.webView.policy;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.base.BaseResponse;
import travelguideapp.ge.travelguide.utility.ResponseHandler;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

class PolicyPresenter extends BasePresenter<PolicyListener> {

    private PolicyListener policyListener;
    private ApiService apiService;

    public PolicyPresenter() {
    }

    public static PolicyPresenter getInstance() {
        return new PolicyPresenter();
    }

    @Override
    protected void attachView(PolicyListener policyListener) {
        this.policyListener = policyListener;
        this.apiService = RetrofitManager.getApiService();
    }

    @Override
    protected void detachView() {
        try {
            this.policyListener = null;
            this.apiService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getPolicy(TermsPolicyRequest termsPolicyRequest) {
        if (isViewAttached(policyListener)) {
            policyListener.showLoader();
        }

        apiService.getTerms(termsPolicyRequest).enqueue(new BaseResponse<TermsPolicyResponse>() {
            @Override
            protected void onSuccess(Response<TermsPolicyResponse> response) {
                if (isViewAttached(policyListener)) {
                    policyListener.hideLoader();
                    try {
                        policyListener.onGetPolicy(response.body().getPolicy());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, policyListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, policyListener);
            }
        });
    }

}
