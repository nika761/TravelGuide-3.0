package com.example.travelguide.ui.home.presenter;

import com.example.travelguide.ui.home.interfaces.IHomeFragment;
import com.example.travelguide.model.request.PostRequest;
import com.example.travelguide.model.response.PostResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private IHomeFragment iHomeFragment;
    private ApiService apiService;

    public HomePresenter(IHomeFragment iHomeFragment) {
        this.iHomeFragment = iHomeFragment;
        apiService = RetrofitManager.getApiService();
    }

    public void getPosts(String accessToken, PostRequest postRequest){
        apiService.getPosts(accessToken, postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()){
                    iHomeFragment.onGetPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });

    }
}
