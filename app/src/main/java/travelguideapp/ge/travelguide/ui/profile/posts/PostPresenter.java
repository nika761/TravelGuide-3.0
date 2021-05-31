package travelguideapp.ge.travelguide.ui.profile.posts;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.base.BaseResponse;
import travelguideapp.ge.travelguide.utility.ResponseHandler;
import travelguideapp.ge.travelguide.model.parcelable.PostHomeParams;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

class PostPresenter extends BasePresenter<PostListener> {

    private PostListener postListener;
    private ApiService apiService;

    private PostPresenter() {
    }

    public static PostPresenter getInstance() {
        return new PostPresenter();
    }

    @Override
    protected void attachView(PostListener postListener) {
        this.postListener = postListener;
        this.apiService = RetrofitManager.getApiService();
    }

    @Override
    protected void detachView() {
        try {
            this.postListener = null;
            this.apiService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPosts(Object request, PostHomeParams.Type pageType, boolean withLoader) {
        if (withLoader && isViewAttached(postListener))
            postListener.showLoader();

        switch (pageType) {
            case CUSTOMER_POSTS:
            case MY_POSTS:
                apiService.getPostsByUser(((PostByUserRequest) request)).enqueue(postCallback());
                break;

            case FAVORITES:
                apiService.getFavoritePosts(((FavoritePostRequest) request)).enqueue(postCallback());
                break;

            case SEARCH:
                break;
        }
    }

    private BaseResponse<PostResponse> postCallback() {
        return new BaseResponse<PostResponse>() {
            @Override
            protected void onSuccess(Response<PostResponse> response) {
                if (isViewAttached(postListener)) {
                    postListener.hideLoader();
                    try {
                        if (response.body().getPosts().size() > 0) {
                            postListener.onGetPosts(response.body().getPosts());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, postListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, postListener);
            }
        };
    }


}
