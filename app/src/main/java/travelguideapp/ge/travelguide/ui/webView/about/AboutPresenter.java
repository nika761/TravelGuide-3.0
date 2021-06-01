package travelguideapp.ge.travelguide.ui.webView.about;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.model.request.AboutRequest;
import travelguideapp.ge.travelguide.model.response.AboutResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;
import travelguideapp.ge.travelguide.network.ErrorHandler;

class AboutPresenter extends BasePresenter<AboutListener> {

    private final ApiService apiService;

    private AboutPresenter(AboutListener aboutListener) {
        super.attachView(aboutListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static AboutPresenter with(AboutListener aboutListener) {
        return new AboutPresenter(aboutListener);
    }

    void getAbout(AboutRequest aboutRequest) {
        super.showLoader();
        apiService.getAbout(aboutRequest).enqueue(new BaseCallback<AboutResponse>(this) {
            @Override
            protected void onSuccess(Response<AboutResponse> response) {
                try {
                    if (isViewAttached())
                        listener.onGetAbout(response.body().getAbout());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
