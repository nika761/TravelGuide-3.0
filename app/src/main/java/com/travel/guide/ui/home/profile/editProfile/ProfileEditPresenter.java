package com.travel.guide.ui.home.profile.editProfile;

import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.request.UpdateProfileRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.model.response.UpdateProfileResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ProfileEditPresenter {
    private ProfileEditListener listener;
    private ApiService apiService;

    ProfileEditPresenter(ProfileEditListener profileEditListener) {
        this.listener = profileEditListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(accessToken, profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getStatus() == 0)
                        listener.onGetProfile(response.body().getUserinfo());

                    else
                        listener.onGetError(String.valueOf(response.body().getStatus()));

                } else {
                    listener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                listener.onGetError(t.getMessage());
            }
        });
    }

    void updateProfile(String accessToken, UpdateProfileRequest updateProfileRequest) {
        apiService.updateProfile(accessToken, updateProfileRequest).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onUpdateProfile(response.body());
                } else {
                    listener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                listener.onGetError(t.getMessage());
            }
        });
    }
}
