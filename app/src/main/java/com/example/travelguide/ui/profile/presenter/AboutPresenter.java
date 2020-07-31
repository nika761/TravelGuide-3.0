package com.example.travelguide.ui.profile.presenter;

import com.example.travelguide.ui.profile.interfaces.IAboutFragment;
import com.example.travelguide.model.request.AboutRequestModel;
import com.example.travelguide.model.response.AboutResponseModel;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutPresenter {
    private IAboutFragment iAboutFragment;
    private ApiService apiService;

    public AboutPresenter(IAboutFragment iAboutFragment) {
        this.iAboutFragment = iAboutFragment;
        apiService = RetrofitManager.getApiService();
    }

    public void sendAboutRequest(AboutRequestModel aboutRequestModel){
        apiService.getAbout(aboutRequestModel).enqueue(new Callback<AboutResponseModel>() {
            @Override
            public void onResponse(Call<AboutResponseModel> call, Response<AboutResponseModel> response) {
                if (response.isSuccessful()){
                    iAboutFragment.onGetAboutResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<AboutResponseModel> call, Throwable t) {

            }
        });
    }

}
