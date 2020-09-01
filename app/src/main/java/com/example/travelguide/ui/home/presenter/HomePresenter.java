package com.example.travelguide.ui.home.presenter;

import android.util.Log;

import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.SetPostFavoriteRequest;
import com.example.travelguide.model.request.SetPostViewRequest;
import com.example.travelguide.model.request.SetStoryLikeRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetPostViewResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
import com.example.travelguide.ui.home.interfaces.IHomeFragment;
import com.example.travelguide.model.request.PostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.home.interfaces.OnLoadFinishListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private OnLoadFinishListener onLoadFinishListener;
    private ApiService apiService;

    public HomePresenter(OnLoadFinishListener onLoadFinishListener) {
        this.onLoadFinishListener = onLoadFinishListener;
        apiService = RetrofitManager.getApiService();
    }

    public void getPosts(String accessToken, PostRequest postRequest) {
        apiService.getPosts(accessToken, postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("orrrer", response.message() + "asdasd");
                    onLoadFinishListener.onGetPosts(response.body());
                } else {
                    Log.e("orrrer", response.toString() + "asdasda");
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("orrrer", t.getMessage() + "Asdasd");
            }
        });
    }

    public void setPostView(String accessToken, SetPostViewRequest setPostViewRequest) {
        apiService.setPostView(accessToken, setPostViewRequest).enqueue(new Callback<SetPostViewResponse>() {
            @Override
            public void onResponse(Call<SetPostViewResponse> call, Response<SetPostViewResponse> response) {
                if (response.isSuccessful() && response.body().getStatus() == 0) {
                    Log.e("mmmmnjasdasdasjjj", response.body().getStatus() + "sent");
                } else {
                    Log.e("mmmmnjasdasdasjjj", String.valueOf(response.body().getStatus()));
                }
            }

            @Override
            public void onFailure(Call<SetPostViewResponse> call, Throwable t) {
                Log.e("mmmmnjasdasdasjjj", String.valueOf(t.getLocalizedMessage()));
            }
        });

    }

    public void setStoryLike(String accessToken, SetStoryLikeRequest setStoryLikeRequest) {
        apiService.setStoryLike(accessToken, setStoryLikeRequest).enqueue(new Callback<SetStoryLikeResponse>() {
            @Override
            public void onResponse(Call<SetStoryLikeResponse> call, Response<SetStoryLikeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onLoadFinishListener.onStoryLiked(response.body());
                    }
                } else {
                    onLoadFinishListener.onStoryLikeError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SetStoryLikeResponse> call, Throwable t) {
                onLoadFinishListener.onStoryLikeError(t.getMessage());
            }
        });
    }


    public void setPostFavorite(String accessToken, SetPostFavoriteRequest setPostFavoriteRequest) {
        apiService.setPostFavorite(accessToken, setPostFavoriteRequest).enqueue(new Callback<SetPostFavoriteResponse>() {
            @Override
            public void onResponse(Call<SetPostFavoriteResponse> call, Response<SetPostFavoriteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onLoadFinishListener.onFavoriteSuccess(response.body());
                    }
                } else {
                    onLoadFinishListener.onFavoriteError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SetPostFavoriteResponse> call, Throwable t) {
                onLoadFinishListener.onFavoriteError(t.getMessage());
            }
        });
    }


    public void follow(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onLoadFinishListener.onFollowSuccess(response.body());
                    }
                } else {
                    onLoadFinishListener.onFollowError(response.message());
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                onLoadFinishListener.onFollowError(t.getMessage());
            }
        });

    }
}
