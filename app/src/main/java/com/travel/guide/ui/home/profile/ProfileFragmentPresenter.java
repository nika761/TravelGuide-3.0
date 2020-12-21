package com.travel.guide.ui.home.profile;

import com.travel.guide.model.request.ProfileRequest;
import com.travel.guide.model.response.ProfileResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ProfileFragmentPresenter {
    private ProfileFragmentListener profileFragmentListener;
    private ApiService apiService;

    ProfileFragmentPresenter(ProfileFragmentListener profileFragmentListener) {
        this.profileFragmentListener = profileFragmentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(accessToken, profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        switch (response.body().getStatus()) {
                            case -100:
                            case -101:
                                profileFragmentListener.onAuthError("Sign In Again");
                                break;

                            case 0:
                                profileFragmentListener.onGetProfile(response.body().getUserinfo());
                                break;
                        }
                    } else
                        profileFragmentListener.onGetError(String.valueOf(response.body().getStatus()));
                } else {
                    profileFragmentListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                profileFragmentListener.onGetError(t.getMessage());
            }
        });
    }

}
