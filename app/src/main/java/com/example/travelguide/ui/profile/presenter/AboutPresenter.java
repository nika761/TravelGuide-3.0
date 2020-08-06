package com.example.travelguide.ui.profile.presenter;

import com.example.travelguide.ui.profile.interfaces.IAboutFragment;
import com.example.travelguide.model.request.AboutRequest;
import com.example.travelguide.model.response.AboutResponse;
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
