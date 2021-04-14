package travelguideapp.ge.travelguide.ui.home.customerUser;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProfilePresenter {
    private final CustomerProfileListener customerProfileListener;
    private final ApiService apiService;

    CustomerProfilePresenter(CustomerProfileListener customerProfileListener) {
        this.customerProfileListener = customerProfileListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(ProfileRequest profileRequest) {
        apiService.getProfile(profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        customerProfileListener.onGetProfile(response.body());
                    } else {
                        customerProfileListener.onError(response.body().getStatus() + response.message());
                    }
                } else {
                    customerProfileListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
                customerProfileListener.onError(t.getMessage());
            }
        });
    }

    public void follow(FollowRequest followRequest) {
        apiService.follow(followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(@NotNull Call<FollowResponse> call, @NotNull Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        customerProfileListener.onFollowSuccess(response.body());
                    } else {
                        customerProfileListener.onError(response.message());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowResponse> call, @NotNull Throwable t) {
                customerProfileListener.onError(t.getMessage());

            }
        });
    }
}
