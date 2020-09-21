package com.example.travelguide.ui.upload;

import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.example.travelguide.model.request.UploadPostRequest;
import com.example.travelguide.model.request.UploadPostRequestModel;
import com.example.travelguide.model.response.UploadPostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPostPresenter {
    private UploadPostListener uploadPostListener;
    private ApiService apiService;

    public UploadPostPresenter(UploadPostListener uploadPostListener) {
        this.uploadPostListener = uploadPostListener;
        apiService = RetrofitManager.getApiService();
    }

    public void uploadStory(String accessToken, UploadPostRequestModel uploadPostRequest) {
        String unc = "application/x-www-form-urlencoded";
        apiService.uploadPost(accessToken, unc, uploadPostRequest).enqueue(new Callback<UploadPostResponse>() {
            @Override
            public void onResponse(Call<UploadPostResponse> call, Response<UploadPostResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.e("zxcv", response.body().getStatus() + " status code  ");
                    Log.e("zxcv", response.body().getResult());
                    Log.e("zxcv", response.code() + " response code ");
                } else {
                    Log.e("zxcv", String.valueOf(response.message()));
                }
//                if (response.isSuccessful())
//                    iEditPost.onStoryUploaded(response.body());
            }

            @Override
            public void onFailure(Call<UploadPostResponse> call, Throwable t) {
                Log.e("zxcv", t.getMessage());
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
