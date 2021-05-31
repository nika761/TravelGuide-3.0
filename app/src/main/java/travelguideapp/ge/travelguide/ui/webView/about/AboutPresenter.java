package travelguideapp.ge.travelguide.ui.webView.about;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.base.BaseResponse;
import travelguideapp.ge.travelguide.utility.ResponseHandler;
import travelguideapp.ge.travelguide.model.request.AboutRequest;
import travelguideapp.ge.travelguide.model.response.AboutResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

class AboutPresenter extends BasePresenter<AboutListener> {

    private AboutListener aboutListener;
    private ApiService apiService;

    private AboutPresenter() {
    }

    public static AboutPresenter getInstance() {
        return new AboutPresenter();
    }

    @Override
    protected void attachView(AboutListener aboutListener) {
        this.aboutListener = aboutListener;
        this.apiService = RetrofitManager.getApiService();
    }

    @Override
    protected void detachView() {
        try {
            this.aboutListener = null;
            this.apiService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getAbout(AboutRequest aboutRequest) {
        if (isViewAttached(aboutListener)) {
            aboutListener.showLoader();
        }

        apiService.getAbout(aboutRequest).enqueue(new BaseResponse<AboutResponse>() {
            @Override
            protected void onSuccess(Response<AboutResponse> response) {
                if (isViewAttached(aboutListener)) {
                    aboutListener.hideLoader();
                    try {
                        aboutListener.onGetAbout(response.body().getAbout());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, aboutListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, aboutListener);
            }

        });
    }

}
