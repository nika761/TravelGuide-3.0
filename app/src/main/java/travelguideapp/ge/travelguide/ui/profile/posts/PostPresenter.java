package travelguideapp.ge.travelguide.ui.profile.posts;

import retrofit2.Response;
import travelguideapp.ge.travelguide.base.BasePresenter;
import travelguideapp.ge.travelguide.network.BaseCallback;
import travelguideapp.ge.travelguide.model.parcelable.HomePostParams;
import travelguideapp.ge.travelguide.model.request.FavoritePostRequest;
import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

class PostPresenter extends BasePresenter<PostListener> {

    private final ApiService apiService;

    private PostPresenter(PostListener postListener) {
        super.attachView(postListener);
        this.apiService = RetrofitManager.getApiService();
    }

    public static PostPresenter with(PostListener postListener) {
        return new PostPresenter(postListener);
    }

    public void getPosts(Object request, HomePostParams.Type pageType) {
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

    private BaseCallback<PostResponse> postCallback() {
        return new BaseCallback<PostResponse>(this) {
            @Override
            protected void onSuccess(Response<PostResponse> response) {
                try {
                    if (isViewAttached()) {
                        if (response.body().getPosts().size() > 0) {
                            listener.onGetPosts(response.body().getPosts());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


}
