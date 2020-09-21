package com.example.travelguide.ui.webView.about;

import com.example.travelguide.model.request.AboutRequest;
import com.example.travelguide.model.response.AboutResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutPresenter {
    private AboutListener iAboutFragment;
    private ApiService apiService;

    public AboutPresenter(AboutListener iAboutFragment) {
        this.iAboutFragment = iAboutFragment;
        apiService = RetrofitManager.getApiService();
    }

    public void sendAboutRequest(AboutRequest aboutRequest){
        apiService.getAbout(aboutRequest).enqueue(new Callback<AboutResponse>() {
            @Override
            public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {
                if (response.isSuccessful()){
                    iAboutFragment.onGetAboutResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<AboutResponse> call, Throwable t) {

            }
        });
    }

}
