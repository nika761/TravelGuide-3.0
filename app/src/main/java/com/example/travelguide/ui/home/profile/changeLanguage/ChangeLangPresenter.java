package com.example.travelguide.ui.home.profile.changeLanguage;

import com.example.travelguide.model.request.ChangeLangRequest;
import com.example.travelguide.model.response.ChangeLangResponse;
import com.example.travelguide.model.response.LanguagesResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeLangPresenter {
    private ChangeLangListener iChangeLangFragment;
    private ApiService apiService;

    public ChangeLangPresenter(ChangeLangListener iChangeLangFragment) {
        this.iChangeLangFragment = iChangeLangFragment;
        apiService = RetrofitManager.getApiService();
    }

    public void sentLanguageRequest() {
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

    public void sentChangeLanguageRequest(ChangeLangRequest changeLangRequest, String accessToken) {
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
