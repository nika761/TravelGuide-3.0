package travelguideapp.ge.travelguide.ui.home;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.base.BaseResponse;
import travelguideapp.ge.travelguide.utility.ResponseHandler;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class HomePagePresenter extends BasePresenter<HomePageListener> {

    private HomePageListener homePageListener;
    private ApiService apiService;

    private HomePagePresenter() {
    }

    public static HomePagePresenter attach(HomePageListener homePageListener) {
        HomePagePresenter homePagePresenter = new HomePagePresenter();
        homePagePresenter.attachView(homePageListener);
        return homePagePresenter;
    }

    @Override
    protected void attachView(HomePageListener homePageListener) {
        this.homePageListener = homePageListener;
        this.apiService = RetrofitManager.getApiService();
    }

    @Override
    protected void detachView() {
        try {
            this.homePageListener = null;
            this.apiService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getProfile(ProfileRequest profileRequest, boolean withLoader) {
        if (withLoader && isViewAttached(homePageListener)) {
            homePageListener.showLoader();
        }

        apiService.getProfile(profileRequest).enqueue(new BaseResponse<ProfileResponse>() {
            @Override
            protected void onSuccess(Response<ProfileResponse> response) {
                if (isViewAttached(homePageListener)) {
                    homePageListener.hideLoader();

                    if (response.body().getStatus() == 0) {
                        homePageListener.onGetProfile(response.body().getUserinfo());
                    }

                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, homePageListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, homePageListener);
            }
        });
    }


}
