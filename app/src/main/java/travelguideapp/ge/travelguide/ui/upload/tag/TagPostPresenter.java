package travelguideapp.ge.travelguide.ui.upload.tag;

import travelguideapp.ge.travelguide.model.request.SearchFollowersRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class TagPostPresenter {

    private TagPostListener tagPostListener;
    private ApiService apiService;

    TagPostPresenter(TagPostListener tagPostListener) {
        this.tagPostListener = tagPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getHashtags(String accessToken, SearchHashtagRequest hashtagRequest) {
        apiService.getHashtags(accessToken, hashtagRequest).enqueue(new Callback<HashtagResponse>() {
            @Override
            public void onResponse(Call<HashtagResponse> call, Response<HashtagResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            tagPostListener.onGetHashtags(response.body().getHashtags());
                            break;
                        case 1:
                            tagPostListener.onGetError(response.message());
                            break;
                    }
                } else {
                    tagPostListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HashtagResponse> call, Throwable t) {
                tagPostListener.onGetError(t.getMessage());
            }
        });
    }

    void searchFollowers(String accessToken, SearchFollowersRequest searchFollowersRequest) {
        apiService.searchFollowers(accessToken, searchFollowersRequest).enqueue(new Callback<FollowerResponse>() {
            @Override
            public void onResponse(Call<FollowerResponse> call, Response<FollowerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            tagPostListener.onGetFollowers(response.body().getFollowers());
                            break;
                        case 1:
                            tagPostListener.onGetError(response.message());
                            break;
                    }
                } else {
                    tagPostListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<FollowerResponse> call, Throwable t) {
                tagPostListener.onGetError(t.getMessage());

            }
        });
    }
}
