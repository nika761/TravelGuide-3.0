package com.example.travelguide.ui.profile.presenter;

import com.example.travelguide.model.request.ProfileRequestModel;
import com.example.travelguide.model.response.ProfileResponseModel;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.profile.interfaces.IProfileFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter {
    private IProfileFragment iProfileFragment;
    private ApiService apiService;

    public ProfilePresenter(IProfileFragment iProfileFragment) {
        this.iProfileFragment = iProfileFragment;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getProfile(String accessToken, ProfileRequestModel profileRequestModel) {
        apiService.getProfile(accessToken, profileRequestModel).enqueue(new Callback<ProfileResponseModel>() {
            @Override
            public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                if (response.isSuccessful()) {
                    iProfileFragment.onGetProfile(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponseModel> call, Throwable t) {

            }
        });
    }

}
