package com.travel.guide.ui.searchPost;

import com.travel.guide.model.request.PostByHashtagRequest;
import com.travel.guide.model.request.PostByLocationRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPostPresenter {
    private SearchPostListener searchPostListener;
    private ApiService apiService;

    SearchPostPresenter(SearchPostListener searchPostListener) {
        this.searchPostListener = searchPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getPostsByLocation(String accessToken, PostByLocationRequest postByLocationRequest) {
        apiService.getPostsByLocation(accessToken, postByLocationRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0)
                        searchPostListener.onGetPosts(response.body());
                    else
                        searchPostListener.onGetPostError(response.message());
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

    public void getPostsByHashtag(String accessToken, PostByHashtagRequest postByHashtagRequest) {
        apiService.getPostsByHashtag(accessToken, postByHashtagRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0)
                        searchPostListener.onGetPosts(response.body());
                    else
                        searchPostListener.onGetPostError(response.message());
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
