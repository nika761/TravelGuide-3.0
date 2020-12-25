package travelguideapp.ge.travelguide.ui.home.home;

import travelguideapp.ge.travelguide.model.request.DeleteStoryRequest;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.request.SetPostFavoriteRequest;
import travelguideapp.ge.travelguide.model.request.SetPostViewRequest;
import travelguideapp.ge.travelguide.model.request.SetStoryLikeRequest;
import travelguideapp.ge.travelguide.model.request.SharePostRequest;
import travelguideapp.ge.travelguide.model.response.DeleteStoryResponse;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.SetPostFavoriteResponse;
import travelguideapp.ge.travelguide.model.response.SetPostViewResponse;
import travelguideapp.ge.travelguide.model.response.SetStoryLikeResponse;
import travelguideapp.ge.travelguide.model.request.PostRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.model.response.SharePostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

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
                        case -101:
                            homeFragmentListener.onAuthError("Sign In Again");
                            break;

                        case 0:
                            if (response.body().getPosts() != null) {
                                if (response.body().getPosts().size() > 0) {
                                    homeFragmentListener.onGetPosts(response.body().getPosts());
                                }
                            }
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

    void getUserPosts(String accessToken, PostByUserRequest postByUserRequest) {
        apiService.getPostsByUser(accessToken, postByUserRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0)
                            homeFragmentListener.onGetPosts(response.body().getPosts());
                    } else {
                        homeFragmentListener.onError(response.message());
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
                    if (response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0)
                            homeFragmentListener.onGetPosts(response.body().getPosts());
                    } else
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

    void deleteStory(String accessToken, DeleteStoryRequest deleteStoryRequest) {
        apiService.deleteStory(accessToken, deleteStoryRequest).enqueue(new Callback<DeleteStoryResponse>() {
            @Override
            public void onResponse(Call<DeleteStoryResponse> call, Response<DeleteStoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        homeFragmentListener.onStoryDeleted(response.body());
                    }
                } else {
                    homeFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<DeleteStoryResponse> call, Throwable t) {
                homeFragmentListener.onError(t.getMessage());
            }
        });
    }

    void setPostViews(String accessToken, SetPostViewRequest setPostViewRequest) {
        apiService.setPostView(accessToken, setPostViewRequest).enqueue(new Callback<SetPostViewResponse>() {
            @Override
            public void onResponse(Call<SetPostViewResponse> call, Response<SetPostViewResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.e("sadzxccxbv", "sworia bliad" + response.code());
//                    Log.e("sadzxccxbv", "sworia bliad" + response.body().getStatus());
//                } else {
//                    Log.e("sadzxccxbv", response.message());
//                }
            }

            @Override
            public void onFailure(Call<SetPostViewResponse> call, Throwable t) {
//                Log.e("sadzxccxbv", t.getMessage());
            }
        });
    }

}
