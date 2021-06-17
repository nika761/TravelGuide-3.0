package travelguideapp.ge.travelguide.ui.profile.follow;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.model.request.FollowRequest;
import travelguideapp.ge.travelguide.model.request.FollowersRequest;
import travelguideapp.ge.travelguide.model.request.FollowingRequest;
import travelguideapp.ge.travelguide.model.response.FollowResponse;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FollowingResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.network.RetrofitManager;

class FollowFragmentPresenter extends BasePresenter<FollowFragmentListener> {

    private final ApiService apiService;

    private FollowFragmentPresenter(FollowFragmentListener followFragmentListener) {
        super.attachView(followFragmentListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static FollowFragmentPresenter with(FollowFragmentListener followFragmentListener) {
        return new FollowFragmentPresenter(followFragmentListener);
    }

    void getFollowing(FollowingRequest followingRequest) {
        super.showLoader();
        apiService.getFollowing(followingRequest).enqueue(new BaseCallback<FollowingResponse>(this) {
            @Override
            protected void onSuccess(Response<FollowingResponse> response) {
                try {
                    if (response.body().getFollowings().size() > 0) {
                        if (isViewAttached())
                            listener.onGetFollowData(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void getFollowers(FollowersRequest followersRequest) {
        super.showLoader();
        apiService.getFollowers(followersRequest).enqueue(new BaseCallback<FollowerResponse>(this) {
            @Override
            protected void onSuccess(Response<FollowerResponse> response) {
                try {
                    if (response.body().getFollowers().size() > 0) {
                        if (isViewAttached())
                            listener.onGetFollowData(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    void startAction(FollowRequest followRequest) {
        super.showLoader();
        apiService.follow(followRequest).enqueue(new BaseCallback<FollowResponse>(this) {
            @Override
            protected void onSuccess(Response<FollowResponse> response) {
                listener.onFollowActionDone(response.body());
            }
        });
    }

}
