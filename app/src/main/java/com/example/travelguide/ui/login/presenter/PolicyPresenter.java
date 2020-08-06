package com.example.travelguide.ui.login.presenter;

import com.example.travelguide.model.response.TermsPolicyResponse;
import com.example.travelguide.ui.login.interfaces.IPolicyFragment;
import com.example.travelguide.model.request.TermsPolicyRequest;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolicyPresenter {
    private IPolicyFragment iPolicyFragment;
    private ApiService apiService;

    public PolicyPresenter(IPolicyFragment iPolicyFragment) {
        this.iPolicyFragment = iPolicyFragment;
        apiService = RetrofitManager.getApiService();
    }

    public void sendPolicyResponse(TermsPolicyRequest termsPolicyRequest) {
        apiService.getTerms(termsPolicyRequest).enqueue(new Callback<TermsPolicyResponse>() {
            @Override
            public void onResponse(Call<TermsPolicyResponse> call, Response<TermsPolicyResponse> response) {
                if (response.isSuccessful()) {
                    iPolicyFragment.onGetPolicyResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<TermsPolicyResponse> call, Throwable t) {

            }
        });
    }

}
