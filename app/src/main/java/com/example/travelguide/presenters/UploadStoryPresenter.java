package com.example.travelguide.presenters;

import com.example.travelguide.interfaces.IUploadStory;
import com.example.travelguide.model.request.UploadStoryRequest;
import com.example.travelguide.model.response.UploadStoryResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadStoryPresenter {
    private IUploadStory iUploadStory;
    private ApiService apiService;

    public UploadStoryPresenter(IUploadStory iUploadStory) {
        this.iUploadStory = iUploadStory;
        apiService = RetrofitManager.getApiService();
    }

    public void uploadStory(String accessToken, UploadStoryRequest uploadStoryRequest) {
        apiService.uploadStory(accessToken, uploadStoryRequest).enqueue(new Callback<UploadStoryResponse>() {
            @Override
            public void onResponse(Call<UploadStoryResponse> call, Response<UploadStoryResponse> response) {
                if (response.isSuccessful()) {
                    iUploadStory.onStoryUploaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<UploadStoryResponse> call, Throwable t) {

            }
        });
    }
}
