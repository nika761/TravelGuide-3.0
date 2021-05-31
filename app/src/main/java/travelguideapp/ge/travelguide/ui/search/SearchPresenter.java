package travelguideapp.ge.travelguide.ui.search;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.base.BaseResponse;
import travelguideapp.ge.travelguide.utility.ResponseHandler;
import travelguideapp.ge.travelguide.model.request.FullSearchRequest;
import travelguideapp.ge.travelguide.model.request.PostByLocationRequest;
import travelguideapp.ge.travelguide.model.request.SearchFollowersRequest;
import travelguideapp.ge.travelguide.model.request.SearchHashtagRequest;
import travelguideapp.ge.travelguide.model.response.FollowerResponse;
import travelguideapp.ge.travelguide.model.response.FullSearchResponse;
import travelguideapp.ge.travelguide.model.response.HashtagResponse;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

public class SearchPresenter extends BasePresenter<SearchListener> {

    private SearchListener searchListener;
    private ApiService apiService;

    private SearchPresenter() {
    }

    public static SearchPresenter attach(SearchListener searchListener) {
        SearchPresenter searchPresenter = new SearchPresenter();
        searchPresenter.attachView(searchListener);
        return searchPresenter;
    }

    @Override
    protected void attachView(SearchListener searchListener) {
        this.searchListener = searchListener;
        this.apiService = RetrofitManager.getApiService();
    }

    @Override
    protected void detachView() {
        try {
            this.searchListener = null;
            this.apiService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fullSearch(FullSearchRequest fullSearchRequest, boolean showLoader) {
        if (isViewAttached(searchListener) && showLoader) {
            searchListener.showLoader();
        }
        apiService.searchAll(fullSearchRequest).enqueue(new BaseResponse<FullSearchResponse>() {
            @Override
            protected void onSuccess(Response<FullSearchResponse> response) {
                if (isViewAttached(searchListener)) {
                    searchListener.hideLoader();

                    if (response.body().getStatus() == 0) {
                        searchListener.onGetSearchedData(response.body());
                    }

                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, searchListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, searchListener);
            }
        });
    }

    void getHashtags(SearchHashtagRequest hashtagRequest, boolean showLoader) {
        if (isViewAttached(searchListener) && showLoader) {
            searchListener.showLoader();
        }
        apiService.getHashtags(hashtagRequest).enqueue(new BaseResponse<HashtagResponse>() {
            @Override
            protected void onSuccess(Response<HashtagResponse> response) {
                if (isViewAttached(searchListener)) {
                    searchListener.hideLoader();
                    if (response.body().getStatus() == 0) {
                        searchListener.onGetHashtags(response.body().getHashtags());
                    }
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, searchListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, searchListener);
            }
        });
    }

    void getFollowers(SearchFollowersRequest searchFollowersRequest, boolean showLoader) {
        if (isViewAttached(searchListener) && showLoader) {
            searchListener.showLoader();
        }
        apiService.searchFollowers(searchFollowersRequest).enqueue(new BaseResponse<FollowerResponse>() {
            @Override
            protected void onSuccess(Response<FollowerResponse> response) {
                if (isViewAttached(searchListener)) {
                    searchListener.hideLoader();

                    if (response.body().getFollowers().size() > 0) {
                        searchListener.onGetUsers(response.body().getFollowers());
                    }
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, searchListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, searchListener);
            }
        });
    }

    void getPosts(PostByLocationRequest postByLocationRequest, boolean showLoader) {
        if (isViewAttached(searchListener) && showLoader) {
            searchListener.showLoader();
        }
        apiService.getPostsByLocation(postByLocationRequest).enqueue(new BaseResponse<PostResponse>() {
            @Override
            protected void onSuccess(Response<PostResponse> response) {
                if (isViewAttached(searchListener)) {
                    searchListener.hideLoader();

                    if (response.body().getPosts().size() > 0) {
                        searchListener.onGetPosts(response.body().getPosts());
                    }
                }
            }

            @Override
            protected void onError(ResponseHandler.Error responseError) {
                onResponseError(responseError, searchListener);
            }

            @Override
            protected void onFail(ResponseHandler.Fail responseFail) {
                onRequestFailed(responseFail, searchListener);
            }
        });
    }

}
