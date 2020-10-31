package com.example.travelguide.ui.home.home;

import com.example.travelguide.model.request.FavoritePostRequest;
import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.SetPostFavoriteRequest;
import com.example.travelguide.model.request.SetStoryLikeRequest;
import com.example.travelguide.model.request.SharePostRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetStoryLikeResponse;
import com.example.travelguide.model.request.PostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.model.response.SharePostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter {
    private HomeFragmentListener homeFragmentListener;
    private ApiService apiService;

    HomeFragmentPresenter(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
        apiService = RetrofitManager.getApiService();
    }

    void getPosts(String accessToken, PostRequest postRequest) {
        apiService.getPosts(accessToken, postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case -100:
                            homeFragmentListener.onLoginError();
                            break;

                        case 0:
                            if (response.body().getPosts() != null) {
                                if (response.body().getPosts().size() > 0) {
                                    homeFragmentListener.onGetPosts(response.body().getPosts());
                                }
                            }
                            break;

                        default:
                            homeFragmentListener.onError("no posts");
                            break;

                    }
                } else {
                    homeFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                homeFragmentListener.onError(t.getMessage());
            }
        });
    }

    void getFavoritePosts(String accessToken, FavoritePostRequest favoritePostRequest) {
        apiService.getFavoritePosts(accessToken, favoritePostRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        homeFragmentListener.onGetPosts(response.body().getPosts());
                    else
                        homeFragmentListener.onError(response.message() + response.body().getStatus());
                } else {
                    homeFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                homeFragmentListener.onError(t.getMessage());
            }
        });
    }

    void setStoryLike(String accessToken, SetStoryLikeRequest setStoryLikeRequest) {
        apiService.setStoryLike(accessToken, setStoryLikeRequest).enqueue(new Callback<SetStoryLikeResponse>() {
            @Override
            public void onResponse(Call<SetStoryLikeResponse> call, Response<SetStoryLikeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        homeFragmentListener.onStoryLiked(response.body());
                    }
                } else {
                    homeFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SetStoryLikeResponse> call, Throwable t) {
                homeFragmentListener.onError(t.getMessage());
            }
        });
    }

    void setPostFavorite(String accessToken, SetPostFavoriteRequest setPostFavoriteRequest) {
        apiService.setPostFavorite(accessToken, setPostFavoriteRequest).enqueue(new Callback<SetPostFavoriteResponse>() {
            @Override
            public void onResponse(Call<SetPostFavoriteResponse> call, Response<SetPostFavoriteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        homeFragmentListener.onFavoriteSuccess(response.body());
                    }
                } else {
                    homeFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SetPostFavoriteResponse> call, Throwable t) {
                homeFragmentListener.onError(t.getMessage());
            }
        });
    }

    void setPostShare(String accessToken, SharePostRequest sharePostRequest) {
        apiService.setPostShare(accessToken, sharePostRequest).enqueue(new Callback<SharePostResponse>() {
            @Override
            public void onResponse(Call<SharePostResponse> call, Response<SharePostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        homeFragmentListener.onShareSuccess(response.body());
                    }
                } else {
                    homeFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SharePostResponse> call, Throwable t) {
                homeFragmentListener.onError(t.getMessage());
            }
        });
    }

    public void follow(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        homeFragmentListener.onFollowSuccess(response.body());
                    }
                } else {
                    homeFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                homeFragmentListener.onError(t.getMessage());
            }
        });

    }

}
