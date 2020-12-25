package travelguideapp.ge.travelguide.ui.home.profile.favorites;

import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FavoritePostPresenter {
    private FavoritePostListener favoritePostListener;
    private ApiService apiService;

    FavoritePostPresenter(FavoritePostListener favoritePostListener) {
        this.favoritePostListener = favoritePostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getFavoritePosts(String accessToken, FavoritePostRequest favoritePostRequest) {
        apiService.getFavoritePosts(accessToken, favoritePostRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0)
                            favoritePostListener.onGetPosts(response.body().getPosts());
                    } else {
                        favoritePostListener.onGetPostsError(response.message());
                    }
                } else {
                    favoritePostListener.onGetPostsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                favoritePostListener.onGetPostsError(t.getMessage());
            }
        });
    }


}
