package travelguideapp.ge.travelguide.ui.home.feed;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.ChooseGoRequest;
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
    private final HomeFragmentListener homeFragmentCallback;
    private final ApiService apiService;

    HomeFragmentPresenter(HomeFragmentListener homeFragmentListener) {
        this.homeFragmentCallback = homeFragmentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getPosts(String accessToken, PostRequest postRequest) {
        Log.e("postascxvx", "aq shemovida ");
        apiService.getPosts(accessToken, postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostResponse> call, @NotNull Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case -100:
                        case -101:
                            homeFragmentCallback.onAuthError("Sign In Again");
                            break;

                        case 0:
                            if (response.body().getPosts() != null) {
                                if (response.body().getPosts().size() > 0) {
                                    homeFragmentCallback.onGetPosts(response.body().getPosts());
                                }
                            }
                            break;
                    }
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });
    }

    void getUserPosts(String accessToken, PostByUserRequest postByUserRequest) {
        apiService.getPostsByUser(accessToken, postByUserRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostResponse> call, @NotNull Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0)
                            homeFragmentCallback.onGetPosts(response.body().getPosts());
                    } else {
                        homeFragmentCallback.onError(response.message());
                    }
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });
    }

    void getFavoritePosts(String accessToken, FavoritePostRequest favoritePostRequest) {
        apiService.getFavoritePosts(accessToken, favoritePostRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostResponse> call, @NotNull Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0)
                            homeFragmentCallback.onGetPosts(response.body().getPosts());
                    } else
                        homeFragmentCallback.onError(response.message() + response.body().getStatus());
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });
    }

    void setStoryLike(String accessToken, SetStoryLikeRequest setStoryLikeRequest) {
        apiService.setStoryLike(accessToken, setStoryLikeRequest).enqueue(new Callback<SetStoryLikeResponse>() {
            @Override
            public void onResponse(@NotNull Call<SetStoryLikeResponse> call, @NotNull Response<SetStoryLikeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            switch (response.body().getStatus()) {
                                case 0:
                                    homeFragmentCallback.onStoryLiked();
                                case 1:
                                    homeFragmentCallback.onStoryUnLiked();
                                    break;

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SetStoryLikeResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });
    }

    void setPostFavorite(String accessToken, SetPostFavoriteRequest setPostFavoriteRequest) {
        apiService.setPostFavorite(accessToken, setPostFavoriteRequest).enqueue(new Callback<SetPostFavoriteResponse>() {
            @Override
            public void onResponse(@NotNull Call<SetPostFavoriteResponse> call, @NotNull Response<SetPostFavoriteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            switch (response.body().getStatus()) {
                                case 0:
                                    homeFragmentCallback.onFavoriteAdded();
                                case 1:
                                    homeFragmentCallback.onFavoriteRemoved();
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SetPostFavoriteResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });
    }

    void setPostShare(String accessToken, SharePostRequest sharePostRequest) {
        apiService.setPostShare(accessToken, sharePostRequest).enqueue(new Callback<SharePostResponse>() {
            @Override
            public void onResponse(@NotNull Call<SharePostResponse> call, @NotNull Response<SharePostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            switch (response.body().getStatus()) {
                                case 0:
                                    homeFragmentCallback.onShared(response.body());
                                    break;
                                case 1:
                                    /*Not shared status from response*/
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SharePostResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });
    }

    public void follow(String accessToken, FollowRequest followRequest) {
        apiService.follow(accessToken, followRequest).enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(@NotNull Call<FollowResponse> call, @NotNull Response<FollowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            switch (response.body().getStatus()) {
                                case 0:
                                    homeFragmentCallback.onFollowAdded();
                                case 1:
                                    homeFragmentCallback.onFollowRemoved();
                                    break;

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });

    }

    void deleteStory(String accessToken, DeleteStoryRequest deleteStoryRequest) {
        apiService.deleteStory(accessToken, deleteStoryRequest).enqueue(new Callback<DeleteStoryResponse>() {
            @Override
            public void onResponse(@NotNull Call<DeleteStoryResponse> call, @NotNull Response<DeleteStoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        homeFragmentCallback.onStoryDeleted(response.body());
                    }
                } else {
                    homeFragmentCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<DeleteStoryResponse> call, @NotNull Throwable t) {
                homeFragmentCallback.onError(t.getMessage());
            }
        });
    }

    void setPostViews(String accessToken, SetPostViewRequest setPostViewRequest) {
        apiService.setPostView(accessToken, setPostViewRequest).enqueue(new Callback<SetPostViewResponse>() {
            @Override
            public void onResponse(@NotNull Call<SetPostViewResponse> call, @NotNull Response<SetPostViewResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.e("sadzxccxbv", "sworia bliad" + response.code());
//                    Log.e("sadzxccxbv", "sworia bliad" + response.body().getStatus());
//                } else {
//                    Log.e("sadzxccxbv", response.message());
//                }
            }

            @Override
            public void onFailure(@NotNull Call<SetPostViewResponse> call, @NotNull Throwable t) {

            }
        });
    }

    void goChoosed(String accessToken, ChooseGoRequest chooseGoRequest) {
        apiService.chooseGo(accessToken, chooseGoRequest).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
//                if (response.isSuccessful()) {
//                    Log.e("sadzxccxbv", "sworia bliad" + response.code());
//                    Log.e("sadzxccxbv", "sworia bliad" + response.body().getStatus());
//                } else {
//                    Log.e("sadzxccxbv", response.message());
//                }
            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {

            }
        });
    }



}
