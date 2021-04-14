package travelguideapp.ge.travelguide.ui.upload;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.model.request.UploadPostRequest;
import travelguideapp.ge.travelguide.model.response.UploadPostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPostPresenter {
    private final UploadPostListener uploadPostListener;
    private final ApiService apiService;

    UploadPostPresenter(UploadPostListener uploadPostListener) {
        this.uploadPostListener = uploadPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void uploadStory(UploadPostRequest uploadPostRequest) {
        String unc = "application/x-www-form-urlencoded";
        apiService.uploadPost(unc, uploadPostRequest).enqueue(new Callback<UploadPostResponse>() {
            @Override
            public void onResponse(@NotNull Call<UploadPostResponse> call, @NotNull Response<UploadPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        uploadPostListener.onPostUploaded();
                    } else {
                        uploadPostListener.onPostUploadError(response.body().getResult());
                    }
                } else {
                    uploadPostListener.onPostUploadError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UploadPostResponse> call, @NotNull Throwable t) {
                uploadPostListener.onPostUploadError(t.getMessage());
            }
        });
    }


    void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                switch (state) {
                    case COMPLETED:
                        uploadPostListener.onPostUploadedToS3();
                        break;
                    case FAILED:
                        uploadPostListener.onPostUploadError("Failed");
                        break;
                    case CANCELED:
                        uploadPostListener.onPostUploadError("Canceled");
                        break;
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                uploadPostListener.onPostUploadError(ex.getMessage());
            }
        });

    }
}
