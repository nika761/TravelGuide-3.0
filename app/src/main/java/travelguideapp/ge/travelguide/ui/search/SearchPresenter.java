package travelguideapp.ge.travelguide.ui.search;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.model.request.SearchFollowersRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class SearchPresenter {
    private SearchListener searchListener;
    private ApiService apiService;

    public SearchPresenter(SearchListener searchListener) {
        this.searchListener = searchListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getHashtags(String accessToken, SearchHashtagRequest hashtagRequest) {
        apiService.getHashtags(accessToken, hashtagRequest).enqueue(new Callback<HashtagResponse>() {
            @Override
            public void onResponse(Call<HashtagResponse> call, Response<HashtagResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            if (response.body().getHashtags().size() > 0)
                                searchListener.onGetHashtags(response.body().getHashtags());
                            else
                                searchListener.onError(response.message());
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
            public void onFailure(Call<HashtagResponse> call, Throwable t) {
                searchListener.onError(t.getMessage());
            }
        });
    }

    void getFollowers(String accessToken, SearchFollowersRequest searchFollowersRequest) {
        apiService.searchFollowers(accessToken, searchFollowersRequest).enqueue(new Callback<FollowerResponse>() {
            @Override
            public void onResponse(Call<FollowerResponse> call, Response<FollowerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            if (response.body().getFollowers().size() > 0)
                                searchListener.onGetUsers(response.body().getFollowers());
                            else
                                searchListener.onError(response.message());
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
            public void onFailure(Call<FollowerResponse> call, Throwable t) {
                searchListener.onError(t.getMessage());
            }
        });
    }

}