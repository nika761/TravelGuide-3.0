package travelguideapp.ge.travelguide.ui.search;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.model.request.FullSearchRequest;
import travelguideapp.ge.travelguide.model.request.PostByLocationRequest;
import travelguideapp.ge.travelguide.model.request.SearchFollowersRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class SearchPresenter {
    private final SearchListener searchListener;
    private final ApiService apiService;

    public SearchPresenter(SearchListener searchListener) {
        this.searchListener = searchListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void fullSearch(String accessToken, FullSearchRequest fullSearchRequest) {
        apiService.searchAll(accessToken, fullSearchRequest).enqueue(new Callback<FullSearchResponse>() {
            @Override
            public void onResponse(@NotNull Call<FullSearchResponse> call, @NotNull Response<FullSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            searchListener.onGetSearchedData(response.body());
                            break;
                        case 1:
                            searchListener.onError(response.message());
                            break;
                    }
                } else {
                    searchListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FullSearchResponse> call, @NotNull Throwable t) {
                searchListener.onError(t.getMessage());
            }
        });
    }

    void getHashtags(String accessToken, SearchHashtagRequest hashtagRequest) {
        apiService.getHashtags(accessToken, hashtagRequest).enqueue(new Callback<HashtagResponse>() {
            @Override
            public void onResponse(@NotNull Call<HashtagResponse> call, @NotNull Response<HashtagResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            if (response.body().getHashtags().size() > 0)
                                searchListener.onGetHashtags(response.body().getHashtags());
                            break;
                        case 1:
                            searchListener.onError(response.message());
                            break;
                    }
                } else {
                    searchListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<HashtagResponse> call, @NotNull Throwable t) {
                searchListener.onError(t.getMessage());
            }
        });
    }

    void getFollowers(String accessToken, SearchFollowersRequest searchFollowersRequest) {
        apiService.searchFollowers(accessToken, searchFollowersRequest).enqueue(new Callback<FollowerResponse>() {
            @Override
            public void onResponse(@NotNull Call<FollowerResponse> call, @NotNull Response<FollowerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            if (response.body().getFollowers().size() > 0)
                                searchListener.onGetUsers(response.body().getFollowers());
                            break;
                        case 1:
                            searchListener.onError(response.message());
                            break;
                    }
                } else {
                    searchListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowerResponse> call, @NotNull Throwable t) {
                searchListener.onError(t.getMessage());
            }
        });
    }


    void getPosts(String accessToken, PostByLocationRequest postByLocationRequest) {
        apiService.getPostsByLocation(accessToken, postByLocationRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostResponse> call, @NotNull Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            if (response.body().getPosts().size() > 0)
                                searchListener.onGetPosts(response.body().getPosts());
                            break;
                        case 1:
                            searchListener.onError(response.message());
                            break;
                    }
                } else {
                    searchListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostResponse> call, @NotNull Throwable t) {
                searchListener.onError(t.getMessage());
            }
        });
    }

}
