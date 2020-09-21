package com.example.travelguide.ui.customerUser.post;

import com.example.travelguide.model.request.CustomerPostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerPhotoPresenter {
    private CustomerPhotoListener customerPhotoListener;
    private ApiService apiService;

    public CustomerPhotoPresenter(CustomerPhotoListener customerPhotoListener) {
        this.customerPhotoListener = customerPhotoListener;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getCustomerPosts(String accessToken, CustomerPostRequest customerPostRequest) {
        apiService.getCustomerPosts(accessToken, customerPostRequest).enqueue(new Callback<PostResponse>() {
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
