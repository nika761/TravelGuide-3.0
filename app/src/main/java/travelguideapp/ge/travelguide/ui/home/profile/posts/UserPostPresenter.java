package travelguideapp.ge.travelguide.ui.home.profile.posts;

import travelguideapp.ge.travelguide.model.request.PostByUserRequest;
import travelguideapp.ge.travelguide.model.response.PostResponse;
import travelguideapp.ge.travelguide.network.ApiService;
import travelguideapp.ge.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class UserPostPresenter {

    private UserPostListener userPostListener;
    private ApiService apiService;

    UserPostPresenter(UserPostListener userPostListener) {
        this.userPostListener = userPostListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getUserPosts(String accessToken, PostByUserRequest postByUserRequest) {
        apiService.getPostsByUser(accessToken, postByUserRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        if (response.body().getPosts().size() > 0)
                            userPostListener.onGetPosts(response.body().getPosts());
                    } else {
                        userPostListener.onGetPostsError(response.message());
                    }
                } else {
                    userPostListener.onGetPostsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                userPostListener.onGetPostsError(t.getMessage());
            }
        });
    }
}
