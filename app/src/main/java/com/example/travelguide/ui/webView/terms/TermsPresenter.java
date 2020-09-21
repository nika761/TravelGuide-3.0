package com.example.travelguide.ui.webView.terms;

import com.example.travelguide.model.response.TermsPolicyResponse;
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsPresenter {
    private TermsListener iTermsFragment;
    private ApiService apiService;

    public TermsPresenter(TermsListener iTermsFragment) {
        this.iTermsFragment = iTermsFragment;
        apiService = RetrofitManager.getApiService();
    }

    public void sendTermsResponse(TermsPolicyRequest termsPolicyRequest) {
        apiService.getTerms(termsPolicyRequest).enqueue(new Callback<TermsPolicyResponse>() {
            @Override
            public void onResponse(Call<TermsPolicyResponse> call, Response<TermsPolicyResponse> response) {
                if (response.isSuccessful()) {
                    iTermsFragment.onGetTermsResult(response.body());
                }

            }

            @Override
            public void onFailure(Call<TermsPolicyResponse> call, Throwable t) {

            }
        });
    }

}
