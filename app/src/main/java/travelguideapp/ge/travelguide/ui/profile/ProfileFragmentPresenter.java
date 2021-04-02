package travelguideapp.ge.travelguide.ui.profile;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ProfileFragmentPresenter {
    private final ProfileFragmentListener profileFragmentListener;
    private final ApiService apiService;

    ProfileFragmentPresenter(ProfileFragmentListener profileFragmentListener) {
        this.profileFragmentListener = profileFragmentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        switch (response.body().getStatus()) {
                            case -100:
                            case -101:
                                profileFragmentListener.onAuthenticationError("Sign In Again");
                                break;

                            case 0:
                                profileFragmentListener.onGetProfile(response.body().getUserinfo());
                                break;
                        }
                    } else
                        profileFragmentListener.onError(String.valueOf(response.body().getStatus()));
                } else {
                    profileFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
//                if (t instanceof UnknownHostException) {
//                    profileFragmentListener.onConnectionError();
//                } else {
//                    profileFragmentListener.onError(t.getMessage());
//                }
                profileFragmentListener.onError(t.getMessage());
            }
        });
    }

}
