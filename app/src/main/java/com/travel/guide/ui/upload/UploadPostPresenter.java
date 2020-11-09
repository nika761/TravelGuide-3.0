package com.travel.guide.ui.upload;

import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.travel.guide.model.request.UploadPostRequest;
import com.travel.guide.model.response.UploadPostResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPostPresenter {
    private UploadPostListener uploadPostListener;
    private ApiService apiService;

    UploadPostPresenter(UploadPostListener uploadPostListener) {
        this.uploadPostListener = uploadPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void uploadStory(String accessToken, UploadPostRequest uploadPostRequest) {
        String unc = "application/x-www-form-urlencoded";
        apiService.uploadPost(accessToken, unc, uploadPostRequest).enqueue(new Callback<UploadPostResponse>() {
            @Override
            public void onResponse(Call<UploadPostResponse> call, Response<UploadPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        uploadPostListener.onPostUploaded();
                    } else {
                        uploadPostListener.onPostUploadError(response.body().getResult());
                        Log.e("zxcv", response.body().getStatus() + " status code  ");
                    }
                    Log.e("zxcv", response.body().getStatus() + " status code  ");
                    Log.e("zxcv", response.body().getResult());
                    Log.e("zxcv", response.code() + " response code ");
                } else {
                    uploadPostListener.onPostUploadError(response.message());
                }
            }

            @Override
            public void onFailure(Call<UploadPostResponse> call, Throwable t) {
                uploadPostListener.onPostUploadError(t.getMessage());
            }
        });
    }


    public void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    uploadPostListener.onPostUploadedToS3();
                } else if (TransferState.FAILED == state) {
                    uploadPostListener.onPostUploadErrorS3("error");
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                uploadPostListener.onPostUploadErrorS3(ex.getMessage());
            }
        });

    }
}
