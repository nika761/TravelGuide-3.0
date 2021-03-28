package travelguideapp.ge.travelguide.ui.profile.follow;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.FollowersRequest;
import travelguideapp.ge.travelguide.model.request.FollowingRequest;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FollowFragmentPresenter {

    private final FollowFragmentListener listener;
    private final ApiService apiService;

    FollowFragmentPresenter(FollowFragmentListener listener) {
        this.listener = listener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getFollowing(String accessToken, FollowingRequest followingRequest) {
        apiService.getFollowing(accessToken, followingRequest).enqueue(new Callback<FollowingResponse>() {
            @Override
            public void onResponse(@NotNull Call<FollowingResponse> call, @NotNull Response<FollowingResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getFollowings().size() > 0)
                            listener.onGetFollowData(response.body());
                        else
                            listener.onError(null);
                    } else {
                        listener.onError(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowingResponse> call, @NotNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }

    void getFollowers(String accessToken, FollowersRequest followersRequest) {
        apiService.getFollowers(accessToken, followersRequest).enqueue(new Callback<FollowerResponse>() {
            @Override
            public void onResponse(@NotNull Call<FollowerResponse> call, @NotNull Response<FollowerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getFollowers().size() > 0)
                            listener.onGetFollowData(response.body());
                        else
                            listener.onError(null);
                    } else {
                        listener.onError(response.message());
                    }
                } else
                    listener.onError(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<FollowerResponse> call, @NotNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }

    void startAction(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(@NotNull Call<FollowResponse> call, @NotNull Response<FollowResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    listener.onFollowActionDone(response.body());
                else
                    listener.onError(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<FollowResponse> call, @NotNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

}
