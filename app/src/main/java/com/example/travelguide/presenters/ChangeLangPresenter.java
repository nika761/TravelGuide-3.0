package com.example.travelguide.presenters;

import com.example.travelguide.interfaces.IChangeLangFragment;
import com.example.travelguide.model.request.ChangeLangRequestModel;
import com.example.travelguide.model.response.ChangeLangResponseModel;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeLangPresenter {
    private IChangeLangFragment iChangeLangFragment;
    private ApiService apiService;

    public ChangeLangPresenter(IChangeLangFragment iChangeLangFragment) {
        this.iChangeLangFragment = iChangeLangFragment;
        apiService = RetrofitManager.getApiservice();
    }

    public void sentLanguageRequest() {
        apiService.getLanguages().enqueue(new Callback<LanguagesResponseModel>() {
            @Override
            public void onResponse(Call<LanguagesResponseModel> call, Response<LanguagesResponseModel> response) {
                if (response.isSuccessful()) {
                    iChangeLangFragment.onGetLanguages(response.body());
                }
            }

            @Override
            public void onFailure(Call<LanguagesResponseModel> call, Throwable t) {

            }
        });
    }

    public void sentChangeLanguageRequest(ChangeLangRequestModel changeLangRequestModel, String accessToken) {
        apiService.changeLang(accessToken, changeLangRequestModel).enqueue(new Callback<ChangeLangResponseModel>() {
            @Override
            public void onResponse(Call<ChangeLangResponseModel> call, Response<ChangeLangResponseModel> response) {
                if (response.isSuccessful())
                    iChangeLangFragment.onLanguageChange(response.body());
            }

            @Override
            public void onFailure(Call<ChangeLangResponseModel> call, Throwable t) {

            }
        });
    }
}
