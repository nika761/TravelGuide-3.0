package com.example.travelguide.ui.webView.policy;

import com.example.travelguide.model.response.TermsPolicyResponse;
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PolicyPresenter {
    private PolicyListener iPolicyFragment;
    private ApiService apiService;

    PolicyPresenter(PolicyListener iPolicyFragment) {
        this.iPolicyFragment = iPolicyFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    void sendPolicyResponse(TermsPolicyRequest termsPolicyRequest) {
        apiService.getTerms(termsPolicyRequest).enqueue(new Callback<TermsPolicyResponse>() {
            @Override
            public void onResponse(Call<TermsPolicyResponse> call, Response<TermsPolicyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        iPolicyFragment.onGetPolicy(response.body().getPolicy());
                } else {
                    iPolicyFragment.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TermsPolicyResponse> call, Throwable t) {
                iPolicyFragment.onGetError(t.getMessage());
            }
        });
    }

}