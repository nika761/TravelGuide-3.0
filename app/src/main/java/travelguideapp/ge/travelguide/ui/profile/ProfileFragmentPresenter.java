package travelguideapp.ge.travelguide.ui.profile;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.base.BaseResponse;
import travelguideapp.ge.travelguide.utility.ResponseHandler;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

class ProfileFragmentPresenter extends BasePresenter<ProfileFragmentListener> {

    private ProfileFragmentListener listener;
    private ApiService apiService;

    private ProfileFragmentPresenter() {
    }

    public static ProfileFragmentPresenter getInstance() {
        return new ProfileFragmentPresenter();
    }

    @Override
    protected void attachView(ProfileFragmentListener profileFragmentListener) {
        this.listener = profileFragmentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    @Override
    protected void detachView() {
        try {
            this.listener = null;
            this.apiService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getProfile(ProfileRequest profileRequest, boolean withLoader) {
        if (withLoader && isViewAttached(listener)) {
            listener.showLoader();
        }

        apiService.getProfile(profileRequest).enqueue(new BaseResponse<ProfileResponse>() {
            @Override
            protected void onSuccess(Response<ProfileResponse> response) {
                if (isViewAttached(listener)) {
                    listener.hideLoader();
                    if (response.body().getStatus() == 0)
                        listener.onGetProfileInfo(response.body().getUserinfo());
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, listener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, listener);
            }
        });
    }

}
