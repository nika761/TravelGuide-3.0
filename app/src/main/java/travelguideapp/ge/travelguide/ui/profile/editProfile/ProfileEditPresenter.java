package travelguideapp.ge.travelguide.ui.profile.editProfile;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

import travelguideapp.ge.travelguide.enums.UserInfoFields;
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
    private ProfileEditListener callback;
    private ApiService apiService;

    ProfileEditPresenter(ProfileEditListener profileEditListener) {
        this.callback = profileEditListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(accessToken, profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
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
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    void updateProfile(String accessToken, UpdateProfileRequest updateProfileRequest) {
        apiService.updateProfile(accessToken, updateProfileRequest).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onUpdateSuccess(response.body());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
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


    HashMap<UserInfoFields, Boolean> checkFields(UpdateProfileRequest body) {
        HashMap<UserInfoFields, Boolean> fields = new HashMap<>();

        if (checkForNullOrEmpty(body.getName()))
            fields.put(UserInfoFields.NAME, false);
        else
            fields.put(UserInfoFields.NAME, true);

        if (checkForNullOrEmpty(body.getLastname()))
            fields.put(UserInfoFields.SURNAME, false);
        else
            fields.put(UserInfoFields.SURNAME, true);

        if (checkForNullOrEmpty(body.getNickname()))
            fields.put(UserInfoFields.NICKNAME, false);
        else
            fields.put(UserInfoFields.NICKNAME, true);

        if (checkForNullOrEmpty(body.getDate_of_birth()))
            fields.put(UserInfoFields.BIRTH_DATE, false);
        else
            fields.put(UserInfoFields.BIRTH_DATE, true);

        if (checkForNullOrEmpty(body.getEmail()))
            fields.put(UserInfoFields.EMAIL, false);
        else if (!HelperUI.isEmailValid(body.getEmail()))
            fields.put(UserInfoFields.EMAIL, false);
        else
            fields.put(UserInfoFields.EMAIL, true);

        if (checkForNullOrEmpty(body.getPhone_num()))
            fields.put(UserInfoFields.PHONE_NUMBER, false);
        else
            fields.put(UserInfoFields.PHONE_NUMBER, true);

        if (checkForNullOrEmpty(body.getGender()))
            fields.put(UserInfoFields.GENDER, false);
        else
            fields.put(UserInfoFields.GENDER, true);

        return fields;
    }

    private boolean checkForNullOrEmpty(String val) {
        return val == null || val.isEmpty();
    }
}
