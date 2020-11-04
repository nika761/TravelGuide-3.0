package com.travelguide.travelguide.ui.home.profile.editProfile;

import com.travelguide.travelguide.model.request.ProfileRequest;
import com.travelguide.travelguide.model.response.ProfileResponse;
import com.travelguide.travelguide.network.ApiService;
import com.travelguide.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ProfileEditPresenter {
    private ProfileEditListener profileEditListener;
    private ApiService apiService;

    ProfileEditPresenter(ProfileEditListener profileEditListener) {
        this.profileEditListener = profileEditListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(accessToken, profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getStatus() == 0)
                        profileEditListener.onGetProfile(response.body().getUserinfo());

                    else
                        profileEditListener.onGetError(String.valueOf(response.body().getStatus()));

                } else {
                    profileEditListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                profileEditListener.onGetError(t.getMessage());
            }
        });
    }
}
