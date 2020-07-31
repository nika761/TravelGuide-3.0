package com.example.travelguide.ui.login.presenter;

import com.example.travelguide.ui.login.interfaces.IPolicyFragment;
import com.example.travelguide.model.request.TermsPolicyRequestModel;
import com.example.travelguide.model.response.TermsPolicyResponseModel;
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

    public void sendPolicyResponse(TermsPolicyRequestModel termsPolicyRequestModel) {
        apiService.getTerms(termsPolicyRequestModel).enqueue(new Callback<TermsPolicyResponseModel>() {
            @Override
            public void onResponse(Call<TermsPolicyResponseModel> call, Response<TermsPolicyResponseModel> response) {
                if (response.isSuccessful()) {
                    iPolicyFragment.onGetPolicyResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<TermsPolicyResponseModel> call, Throwable t) {

            }
        });
    }

}
