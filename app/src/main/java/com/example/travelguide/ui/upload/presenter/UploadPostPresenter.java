package com.example.travelguide.ui.upload.presenter;

import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.example.travelguide.model.request.UploadPostRequestModel;
import com.example.travelguide.ui.upload.interfaces.IEditPost;
import com.example.travelguide.model.response.UploadPostResponseModel;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPostPresenter {
    private IEditPost iEditPost;
    private ApiService apiService;

    public UploadPostPresenter(IEditPost iEditPost) {
        this.iEditPost = iEditPost;
        apiService = RetrofitManager.getApiService();
    }

    public void uploadStory(String accessToken, UploadPostRequestModel uploadPostRequestModel) {
        apiService.uploadPost(accessToken, uploadPostRequestModel).enqueue(new Callback<UploadPostResponseModel>() {
            @Override
            public void onResponse(Call<UploadPostResponseModel> call, Response<UploadPostResponseModel> response) {

                Log.e("asdasdasd", String.valueOf(response.code()));
                if (response.isSuccessful())
                    iEditPost.onStoryUploaded(response.body());
            }

            @Override
            public void onFailure(Call<UploadPostResponseModel> call, Throwable t) {
                Log.e("ssssss", t.getMessage());

            }
        });
    }


    public void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    iEditPost.onFileUploaded();
                } else if (TransferState.FAILED == state) {
                    iEditPost.onFileUploadError();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                iEditPost.onFileUploadError();
            }
        });

    }
}
