package travelguideapp.ge.travelguide.ui.home.customerUser;

import org.jetbrains.annotations.NotNull;

import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.model.request.ProfileRequest;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.response.ProfileResponse;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import travelguideapp.ge.travelguide.ui.home.HomePageListener;
import travelguideapp.ge.travelguide.ui.home.HomePagePresenter;

public class CustomerProfilePresenter extends BasePresenter<CustomerProfileListener> {
    private final ApiService apiService;

    private CustomerProfilePresenter(CustomerProfileListener customerProfileListener) {
        super.attachView(customerProfileListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static CustomerProfilePresenter with(CustomerProfileListener customerProfileListener) {
        return new CustomerProfilePresenter(customerProfileListener);
    }

    void getProfile(ProfileRequest profileRequest) {
        super.showLoader();
        apiService.getProfile(profileRequest).enqueue(new BaseCallback<ProfileResponse>(this) {
            @Override
            protected void onSuccess(Response<ProfileResponse> response) {
                try {
                    if (isViewAttached()) {
                        if (response.body().getStatus() == 0) {
                            listener.onGetProfile(response.body());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void follow(FollowRequest followRequest) {
        apiService.follow(followRequest).enqueue(new BaseCallback<FollowResponse>(this) {
            @Override
            protected void onSuccess(Response<FollowResponse> response) {
                try {
                    if (isViewAttached()) {
                        listener.onFollowSuccess(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
