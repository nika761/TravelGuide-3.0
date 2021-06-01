package travelguideapp.ge.travelguide.ui.search;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.network.BaseCallback;
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
import travelguideapp.ge.travelguide.network.ErrorHandler;

public class SearchPresenter extends BasePresenter<SearchListener> {

    private final ApiService apiService;

    private SearchPresenter(SearchListener searchListener) {
        super.attachView(searchListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static SearchPresenter with(SearchListener searchListener) {
        return new SearchPresenter(searchListener);
    }

    void fullSearch(FullSearchRequest fullSearchRequest) {
        showLoader();
        apiService.searchAll(fullSearchRequest).enqueue(new BaseCallback<FullSearchResponse>(this) {
            @Override
            protected void onSuccess(Response<FullSearchResponse> response) {
                if (isViewAttached()) {
                    if (response.body().getStatus() == 0) {
                        listener.onGetSearchedData(response.body());
                    }
                }
            }
        });
    }

    void getHashtags(SearchHashtagRequest hashtagRequest) {
        apiService.getHashtags(hashtagRequest).enqueue(new BaseCallback<HashtagResponse>(this) {
            @Override
            protected void onSuccess(Response<HashtagResponse> response) {
                if (isViewAttached()) {
                    if (response.body().getStatus() == 0) {
                        listener.onGetHashtags(response.body().getHashtags());
                    }
                }
            }
        });
    }

    void getFollowers(SearchFollowersRequest searchFollowersRequest, boolean showLoader) {
        if (isViewAttached() && showLoader) {
            listener.showLoader();
        }
        apiService.searchFollowers(searchFollowersRequest).enqueue(new BaseCallback<FollowerResponse>(this) {
            @Override
            protected void onSuccess(Response<FollowerResponse> response) {
                if (isViewAttached()) {
                    if (response.body().getFollowers().size() > 0) {
                        listener.onGetUsers(response.body().getFollowers());
                    }
                }
            }
        });
    }

    void getPosts(PostByLocationRequest postByLocationRequest) {
        apiService.getPostsByLocation(postByLocationRequest).enqueue(new BaseCallback<PostResponse>(this) {
            @Override
            protected void onSuccess(Response<PostResponse> response) {
                if (isViewAttached()) {
                    if (response.body().getPosts().size() > 0) {
                        listener.onGetPosts(response.body().getPosts());
                    }
                }
            }
        });
    }

}
