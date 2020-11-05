package com.travel.guide.ui.webView.about;

import com.travel.guide.model.request.AboutRequest;
import com.travel.guide.model.response.AboutResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AboutPresenter {
    private AboutListener iAboutFragment;
    private ApiService apiService;

    AboutPresenter(AboutListener iAboutFragment) {
        this.iAboutFragment = iAboutFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    void sendAboutRequest(AboutRequest aboutRequest) {
        apiService.getAbout(aboutRequest).enqueue(new Callback<AboutResponse>() {
            @Override
            public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        iAboutFragment.onGetAbout(response.body().getAbout());
                } else {
                    iAboutFragment.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AboutResponse> call, Throwable t) {
                iAboutFragment.onGetError(t.getMessage());
            }
        });
    }

}