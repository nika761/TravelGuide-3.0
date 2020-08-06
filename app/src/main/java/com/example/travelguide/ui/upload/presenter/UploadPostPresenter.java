package com.example.travelguide.ui.upload.presenter;

import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.example.travelguide.model.request.UploadPostRequest;
import com.example.travelguide.model.response.UploadPostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.upload.interfaces.IUploadPostListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPostPresenter {
    private IUploadPostListener uploadPostListener;
    private ApiService apiService;

    public UploadPostPresenter(IUploadPostListener uploadPostListener) {
        this.uploadPostListener = uploadPostListener;
        apiService = RetrofitManager.getApiService();
    }

    public void uploadStory(String accessToken, UploadPostRequest uploadPostRequest) {
        apiService.uploadPost(accessToken, uploadPostRequest).enqueue(new Callback<UploadPostResponse>() {
            @Override
            public void onResponse(Call<UploadPostResponse> call, Response<UploadPostResponse> response) {

                Log.e("asdasdasd", String.valueOf(response.code()));
//                if (response.isSuccessful())
//                    iEditPost.onStoryUploaded(response.body());
            }

            @Override
            public void onFailure(Call<UploadPostResponse> call, Throwable t) {
                Log.e("ssssss", t.getMessage());

            }
        });
    }


    public void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    uploadPostListener.onPostUploaded();
                } else if (TransferState.FAILED == state) {
                    uploadPostListener.onPostUploadError();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                uploadPostListener.onPostUploadError();
            }
        });

    }
}
