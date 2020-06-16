package com.example.travelguide.presenters;

import com.example.travelguide.interfaces.ILanguageActivity;
import com.example.travelguide.model.LanguagesResponseModel;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguagePresenter {
    private ILanguageActivity iLanguageActivity;
    private ApiService service;

    public LanguagePresenter(ILanguageActivity iLanguageActivity) {
        this.iLanguageActivity = iLanguageActivity;
        service = RetrofitManager.getApiservice();
    }

    public void sentLanguageRequest() {
        service.getLanguages().enqueue(new Callback<LanguagesResponseModel>() {
            @Override
            public void onResponse(Call<LanguagesResponseModel> call, Response<LanguagesResponseModel> response) {
                if (response.isSuccessful()) {
                    iLanguageActivity.onGetLanguages(response.body());
                }
            }

            @Override
            public void onFailure(Call<LanguagesResponseModel> call, Throwable t) {

            }
        });
    }
}
