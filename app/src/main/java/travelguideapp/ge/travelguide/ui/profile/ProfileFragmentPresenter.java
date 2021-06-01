package travelguideapp.ge.travelguide.ui.profile;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;
import travelguideapp.ge.travelguide.network.ErrorHandler;

class ProfileFragmentPresenter extends BasePresenter<ProfileFragmentListener> {

    private final ApiService apiService;

    private ProfileFragmentPresenter(ProfileFragmentListener profileFragmentListener) {
        super.attachView(profileFragmentListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static ProfileFragmentPresenter with(ProfileFragmentListener profileFragmentListener) {
        return new ProfileFragmentPresenter(profileFragmentListener);
    }

    void getProfile(ProfileRequest profileRequest, boolean withLoader) {
        if (withLoader)
            showLoader();

        apiService.getProfile(profileRequest).enqueue(new BaseCallback<ProfileResponse>(this) {
            @Override
            protected void onSuccess(Response<ProfileResponse> response) {
                if (isViewAttached()) {
                    if (response.body().getStatus() == 0)
                        listener.onGetProfileInfo(response.body().getUserinfo());
                }
            }
        });
    }

}
