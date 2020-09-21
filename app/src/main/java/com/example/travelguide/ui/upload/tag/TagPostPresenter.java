package com.example.travelguide.ui.upload.tag;

import com.example.travelguide.model.request.HashtagRequest;
import com.example.travelguide.model.response.HashtagResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagPostPresenter {

    private TagPostListener tagPostListener;
    private ApiService apiService;

    public TagPostPresenter(TagPostListener tagPostListener) {
        this.tagPostListener = tagPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getHashtags(String accessToken, HashtagRequest hashtagRequest) {
        apiService.getHashtags(accessToken, hashtagRequest).enqueue(new Callback<HashtagResponse>() {
            @Override
            public void onResponse(Call<HashtagResponse> call, Response<HashtagResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    switch (response.body().getStatus()) {
                        case 0:
                            tagPostListener.onGetHashtags(response.body().getHashtags());
                            break;
                        case 1:
                            tagPostListener.onGetHashtagsError(response.message());
                            break;
                    }
                } else {
                    tagPostListener.onGetHashtagsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HashtagResponse> call, Throwable t) {
                tagPostListener.onGetHashtagsError(t.getMessage());
            }
        });
    }
}
