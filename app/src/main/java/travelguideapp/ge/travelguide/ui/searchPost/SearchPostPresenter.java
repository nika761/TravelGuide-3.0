package travelguideapp.ge.travelguide.ui.searchPost;

import travelguideapp.ge.travelguide.model.request.PostByHashtagRequest;
import travelguideapp.ge.travelguide.model.request.PostByLocationRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SearchPostPresenter {

    private final SearchPostListener searchPostListener;
    private final ApiService apiService;

    SearchPostPresenter(SearchPostListener searchPostListener) {
        this.searchPostListener = searchPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getPostsByLocation(String accessToken, PostByLocationRequest postByLocationRequest) {
        apiService.getPostsByLocation(accessToken, postByLocationRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0) {
                            searchPostListener.onGetPosts(response.body().getPosts());
                        }
                    } else {
                        searchPostListener.onGetPostError(response.message());
                    }
                } else {
                    searchPostListener.onGetPostError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                searchPostListener.onGetPostError(t.getMessage());
            }
        });
    }

    void getPostsByHashtag(String accessToken, PostByHashtagRequest postByHashtagRequest) {
        apiService.getPostsByHashtag(accessToken, postByHashtagRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0) {
                            searchPostListener.onGetPosts(response.body().getPosts());
                        }
                    } else {
                        searchPostListener.onGetPostError(response.message());
                    }
                } else {
                    searchPostListener.onGetPostError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                searchPostListener.onGetPostError(t.getMessage());
            }
        });
    }
}
