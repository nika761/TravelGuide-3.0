package com.travel.guide.ui.home.profile.changeLanguage;

import com.travel.guide.model.request.ChangeLangRequest;
import com.travel.guide.model.response.ChangeLangResponse;
import com.travel.guide.model.response.LanguagesResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ChangeLangPresenter {
    private ChangeLangListener iChangeLangFragment;
    private ApiService apiService;

    ChangeLangPresenter(ChangeLangListener iChangeLangFragment) {
        this.iChangeLangFragment = iChangeLangFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    void sentLanguageRequest() {
        apiService.getLanguages().enqueue(new Callback<LanguagesResponse>() {
            @Override
            public void onResponse(Call<LanguagesResponse> call, Response<LanguagesResponse> response) {
                if (response.isSuccessful()) {
                    iChangeLangFragment.onGetLanguages(response.body());
                }
            }

            @Override
            public void onFailure(Call<LanguagesResponse> call, Throwable t) {
                iChangeLangFragment.onError();
            }
        });
    }

    void sentChangeLanguageRequest(ChangeLangRequest changeLangRequest, String accessToken) {
        apiService.changeLanguage(accessToken, changeLangRequest).enqueue(new Callback<ChangeLangResponse>() {
            @Override
            public void onResponse(Call<ChangeLangResponse> call, Response<ChangeLangResponse> response) {
                if (response.isSuccessful())
                    if (response.body() != null)
                        if (response.body().getStatus() == 0)
                            iChangeLangFragment.onLanguageChange(response.body());
            }

            @Override
            public void onFailure(Call<ChangeLangResponse> call, Throwable t) {
                iChangeLangFragment.onError();
            }
        });
    }
}
