package com.example.travelguide.ui.home.presenter;

import com.example.travelguide.model.request.CustomerPostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;
import com.example.travelguide.ui.home.interfaces.ICustomerPhoto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerPhotoPresenter {
    private ICustomerPhoto iCustomerPhoto;
    private ApiService apiService;

    public CustomerPhotoPresenter(ICustomerPhoto iCustomerPhoto) {
        this.iCustomerPhoto = iCustomerPhoto;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getCustomerPosts(String accessToken, CustomerPostRequest customerPostRequest) {
        apiService.getCustomerPosts(accessToken, customerPostRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 0) {
                        iCustomerPhoto.onGetPosts(response.body());
                    } else {
                        iCustomerPhoto.onGetPostsError(response.message());
                    }
                } else {
                    iCustomerPhoto.onGetPostsError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                iCustomerPhoto.onGetPostsError(t.getMessage());
            }
        });
    }
}
