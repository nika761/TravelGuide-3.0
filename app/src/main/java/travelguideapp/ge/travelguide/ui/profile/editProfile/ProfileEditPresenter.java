package travelguideapp.ge.travelguide.ui.profile.editProfile;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.helper.HelperUI;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.request.UpdateProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.model.response.UpdateProfileResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ProfileEditPresenter {
    private final ProfileEditListener callback;
    private final ApiService apiService;

    ProfileEditPresenter(ProfileEditListener profileEditListener) {
        this.callback = profileEditListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        callback.onGetProfileInfo(response.body().getUserinfo());
                    else
                        callback.onError(String.valueOf(response.body().getStatus()));
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    void updateProfile(String accessToken, UpdateProfileRequest updateProfileRequest) {
        apiService.updateProfile(accessToken, updateProfileRequest).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateProfileResponse> call, @NotNull Response<UpdateProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onUpdateSuccess(response.body());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateProfileResponse> call, @NotNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    void uploadToS3(TransferObserver transferObserver) {
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    callback.onPhotoUploadedToS3();
                } else if (TransferState.FAILED == state) {
                    callback.onError("error");
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                callback.onError(ex.getMessage());
            }
        });
    }

    private boolean checkForNullOrEmpty(String val) {
        return val == null || val.isEmpty();
    }
}
