package travelguideapp.ge.travelguide.ui.profile.posts;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.parcelable.PostHomeParams;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class PostPresenter {

    private final PostListener postListener;
    private final ApiService apiService;

    PostPresenter(PostListener postListener) {
        this.postListener = postListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getPosts(String accessToken, Object request, PostHomeParams.PageType pageType) {
        switch (pageType) {
            case CUSTOMER_POSTS:
            case MY_POSTS:
                apiService.getPostsByUser(accessToken, ((PostByUserRequest) request)).enqueue(getCallback());
                break;

            case FAVORITES:
                apiService.getFavoritePosts(accessToken, ((FavoritePostRequest) request)).enqueue(getCallback());
                break;

            case SEARCH:
                break;
        }
    }

    private Callback<PostResponse> getCallback() {
        return new Callback<PostResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostResponse> call, @NotNull Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0)
                            postListener.onGetPosts(response.body().getPosts());
                    } else {
                        postListener.onError(response.message());
                    }
                } else {
                    postListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostResponse> call, @NotNull Throwable t) {
                postListener.onError(t.getMessage());
            }
        };
    }
}
