package travelguideapp.ge.travelguide.ui.home;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class HomePagePresenter {

    private HomePageListener homePageListener;
    private ApiService apiService;

    public static HomePagePresenter getInstance(HomePageListener homePageListener) {
        HomePagePresenter homePagePresenter = new HomePagePresenter();
        homePagePresenter.homePageListener = homePageListener;
        homePagePresenter.apiService = RetrofitManager.getApiService();
        return homePagePresenter;
    }

//    HomePagePresenter(HomePageListener homePageListener) {
//        this.homePageListener = homePageListener;
//        this.apiService = RetrofitManager.getApiService();
//    }

    void getProfile(String accessToken, ProfileRequest profileRequest) {
        apiService.getProfile(accessToken, profileRequest).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        switch (response.body().getStatus()) {
                            case -100:
                            case -101:
                                homePageListener.onAuthenticationError("Sign In Again");
                                break;

                            case 0:
                                homePageListener.onGetProfile(response.body().getUserinfo());
                                break;
                        }
                    }
//                        homePageListener.onGetError(String.valueOf(response.body().getStatus()));
                } else {
//                    homePageListener.onGetError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
//                homePageListener.onGetError(t.getMessage());
            }
        });
    }


}
