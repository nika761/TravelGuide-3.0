package com.travel.guide.ui.home.home;

import com.travel.guide.model.request.FavoritePostRequest;
import com.travel.guide.model.request.FollowRequest;
import com.travel.guide.model.request.SetPostFavoriteRequest;
import com.travel.guide.model.request.SetStoryLikeRequest;
import com.travel.guide.model.request.SharePostRequest;
import com.travel.guide.model.response.FollowResponse;
import com.travel.guide.model.response.SetPostFavoriteResponse;
import com.travel.guide.model.response.SetStoryLikeResponse;
import com.travel.guide.model.request.PostRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.model.response.SharePostResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter {
    private HomeFragmentListener homeFragmentListener;
    private ApiService apiService;

    HomeFragmentPresenter(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentListener = homeFragmentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getPosts(String accessToken, PostRequest postRequest) {
        apiService.getPosts(accessToken, postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case -100:
                            homeFragmentListener.onAuthError();
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
