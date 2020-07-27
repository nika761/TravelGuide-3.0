package com.example.travelguide.presenters;

import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.example.travelguide.interfaces.IUploadStory;
import com.example.travelguide.model.request.UploadStoryRequestModel;
import com.example.travelguide.model.response.UploadStoryResponseModel;
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

    public void uploadStory(String accessToken, UploadStoryRequestModel uploadStoryRequestModel) {
        apiService.uploadStory(accessToken, uploadStoryRequestModel).enqueue(new Callback<UploadStoryResponseModel>() {
            @Override
            public void onResponse(Call<UploadStoryResponseModel> call, Response<UploadStoryResponseModel> response) {

                Log.e("asdasdasd", String.valueOf(response.code()));
                if (response.isSuccessful())
                    iUploadStory.onStoryUploaded(response.body());
            }

            @Override
            public void onFailure(Call<UploadStoryResponseModel> call, Throwable t) {
                Log.e("ssssss", t.getMessage());

            }
        });
    }


    public void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    iUploadStory.onFileUploaded();
                } else if (TransferState.FAILED == state) {
                    iUploadStory.onFileUploadError();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                iUploadStory.onFileUploadError();
            }
        });

    }
}
