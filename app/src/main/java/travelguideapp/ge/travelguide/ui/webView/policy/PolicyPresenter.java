package travelguideapp.ge.travelguide.ui.webView.policy;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.response.TermsPolicyResponse;
import travelguideapp.ge.travelguide.model.request.TermsPolicyRequest;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PolicyPresenter {
    private final PolicyListener iPolicyFragment;
    private final ApiService apiService;

    PolicyPresenter(PolicyListener iPolicyFragment) {
        this.iPolicyFragment = iPolicyFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    void sendPolicyResponse(TermsPolicyRequest termsPolicyRequest) {
        apiService.getTerms(termsPolicyRequest).enqueue(new Callback<TermsPolicyResponse>() {
            @Override
            public void onResponse(@NotNull Call<TermsPolicyResponse> call, @NotNull Response<TermsPolicyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        iPolicyFragment.onGetPolicy(response.body().getPolicy());
                } else {
                    iPolicyFragment.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<TermsPolicyResponse> call, @NotNull Throwable t) {
                iPolicyFragment.onGetError(t.getMessage());
            }
        });
    }

}
