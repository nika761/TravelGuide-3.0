package com.travel.guide.ui.customerUser.post;

import com.travel.guide.model.request.PostByUserRequest;
import com.travel.guide.model.response.PostResponse;
import com.travel.guide.network.ApiService;
import com.travel.guide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CustomerPhotoPresenter {
    private CustomerPhotoListener customerPhotoListener;
    private ApiService apiService;

    CustomerPhotoPresenter(CustomerPhotoListener customerPhotoListener) {
        this.customerPhotoListener = customerPhotoListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getCustomerPosts(String accessToken, PostByUserRequest customerPostRequest) {
        apiService.getPostsByUser(accessToken, customerPostRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        customerPhotoListener.onGetPosts(response.body());
                    } else {
                        customerPhotoListener.onGetPostsError(response.message());
                    }
                } else {
                    customerPhotoListener.onGetPostsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                customerPhotoListener.onGetPostsError(t.getMessage());
            }
        });
    }
}
