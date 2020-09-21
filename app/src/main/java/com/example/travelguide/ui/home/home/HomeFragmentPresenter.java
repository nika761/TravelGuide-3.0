package com.example.travelguide.ui.home.home;

import android.util.Log;

import com.example.travelguide.model.request.FollowRequest;
import com.example.travelguide.model.request.SetPostFavoriteRequest;
import com.example.travelguide.model.request.SetPostViewRequest;
import com.example.travelguide.model.request.SetStoryLikeRequest;
import com.example.travelguide.model.request.SharePostRequest;
import com.example.travelguide.model.response.FollowResponse;
import com.example.travelguide.model.response.SetPostFavoriteResponse;
import com.example.travelguide.model.response.SetPostViewResponse;
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
                if (response.isSuccessful()) {
                    homeFragmentListener.onGetPosts(response.body());
                    if (response.body() != null) {
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().size()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(0).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(1).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(2).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(3).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(4).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(5).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(6).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(7).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(8).getPost_id()));
                        Log.e("postsdsdsd", String.valueOf(response.body().getPosts().get(9).getPost_id()));
                    }
                } else {
                    homeFragmentListener.onGetPostsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                homeFragmentListener.onGetPostsError(t.getMessage());
            }

        });
    }

    void getLazyPosts(String accessToken, PostRequest postRequest) {
        apiService.getPosts(accessToken, postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                        homeFragmentListener.onGetLazyPosts(response.body());
                } else {
                    homeFragmentListener.onGetPostsError(response.message());

                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                homeFragmentListener.onGetPostsError(t.getMessage());
            }
        });

    }

    void setPostView(String accessToken, SetPostViewRequest setPostViewRequest) {
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

    void setStoryLike(String accessToken, SetStoryLikeRequest setStoryLikeRequest) {
        apiService.setStoryLike(accessToken, setStoryLikeRequest).enqueue(new Callback<SetStoryLikeResponse>() {
            @Override
            public void onResponse(Call<SetStoryLikeResponse> call, Response<SetStoryLikeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        homeFragmentListener.onStoryLiked(response.body());
                    }
                } else {
                    homeFragmentListener.onStoryLikeError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SetStoryLikeResponse> call, Throwable t) {
                homeFragmentListener.onStoryLikeError(t.getMessage());
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
                    homeFragmentListener.onFavoriteError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SetPostFavoriteResponse> call, Throwable t) {
                homeFragmentListener.onFavoriteError(t.getMessage());
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
                    homeFragmentListener.onShareError(response.message());
                }
            }

            @Override
            public void onFailure(Call<SharePostResponse> call, Throwable t) {
                homeFragmentListener.onShareError(t.getMessage());
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
                    homeFragmentListener.onFollowError(response.message());
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                homeFragmentListener.onFollowError(t.getMessage());
            }
        });

    }
}
