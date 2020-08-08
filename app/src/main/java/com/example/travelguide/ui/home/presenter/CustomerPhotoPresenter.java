package com.example.travelguide.ui.home.presenter;

import com.example.travelguide.model.request.CustomerPostRequest;
import com.example.travelguide.model.response.CustomerPostResponse;
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
        apiService.getCustomerPosts(accessToken, customerPostRequest).enqueue(new Callback<CustomerPostResponse>() {
            @Override
            public void onResponse(Call<CustomerPostResponse> call, Response<CustomerPostResponse> response) {
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
            public void onFailure(Call<CustomerPostResponse> call, Throwable t) {
                iCustomerPhoto.onGetPostsError(t.getMessage());
            }
        });
    }
}
