package com.travelguide.travelguide.ui.home.profile.changeLanguage;

import com.travelguide.travelguide.model.request.ChangeLangRequest;
import com.travelguide.travelguide.model.response.ChangeLangResponse;
import com.travelguide.travelguide.model.response.LanguagesResponse;
import com.travelguide.travelguide.network.ApiService;
import com.travelguide.travelguide.network.RetrofitManager;

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

            }
        });
    }

    void sentChangeLanguageRequest(ChangeLangRequest changeLangRequest, String accessToken) {
        apiService.changeLanguage(accessToken, changeLangRequest).enqueue(new Callback<ChangeLangResponse>() {
            @Override
            public void onResponse(Call<ChangeLangResponse> call, Response<ChangeLangResponse> response) {
                if (response.isSuccessful())
                    iChangeLangFragment.onLanguageChange(response.body());
            }

            @Override
            public void onFailure(Call<ChangeLangResponse> call, Throwable t) {

            }
        });
    }
}
