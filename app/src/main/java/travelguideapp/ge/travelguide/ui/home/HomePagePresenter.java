package travelguideapp.ge.travelguide.ui.home;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.network.ErrorHandler;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class HomePagePresenter extends BasePresenter<HomePageListener> {

    private final ApiService apiService;

    private HomePagePresenter(HomePageListener homePageListener) {
        super.attachView(homePageListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static HomePagePresenter with(HomePageListener homePageListener) {
        return new HomePagePresenter(homePageListener);
    }

    void getProfile(ProfileRequest profileRequest) {
        apiService.getProfile(profileRequest).enqueue(new BaseCallback<ProfileResponse>(this) {
            @Override
            protected void onSuccess(Response<ProfileResponse> response) {
                if (isViewAttached()) {
                    try {
                        if (response.body().getStatus() == 0) {
                            listener.onGetProfile(response.body().getUserinfo());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
