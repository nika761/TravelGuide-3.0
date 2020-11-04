package com.travelguide.travelguide.ui.language;

import com.travelguide.travelguide.model.response.LanguagesResponse;
import com.travelguide.travelguide.network.ApiService;
import com.travelguide.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguagePresenter {
    private LanguageListener iLanguageActivity;
    private ApiService service;

    public LanguagePresenter(LanguageListener iLanguageActivity) {
        this.iLanguageActivity = iLanguageActivity;
        this.service = RetrofitManager.getApiService();
    }

    public void sentLanguageRequest() {
        service.getLanguages().enqueue(new Callback<LanguagesResponse>() {
            @Override
            public void onResponse(Call<LanguagesResponse> call, Response<LanguagesResponse> response) {
                if (response.isSuccessful()) {
                    iLanguageActivity.onGetLanguages(response.body());
                }
            }

            @Override
            public void onFailure(Call<LanguagesResponse> call, Throwable t) {

            }
        });
    }
}
