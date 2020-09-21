package com.example.travelguide.ui.home.comments;

import android.util.Log;

import com.example.travelguide.model.request.CommentRequest;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CommentFragmentPresenter {
    private CommentFragmentListener commentFragmentListener;
    private ApiService apiService;

    CommentFragmentPresenter(CommentFragmentListener commentFragmentListener) {
        this.commentFragmentListener = commentFragmentListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getStoryComments(String accessToken, CommentRequest commentRequest) {
        apiService.getStoryComments(accessToken, commentRequest).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    commentFragmentListener.onGetComments(response.body());
                    Log.e("comment", "request get");
                    Log.e("comment", response.message());

                } else {
                    commentFragmentListener.onGetCommentsError(response.message());
                    Log.e("comment", response.message());

                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                commentFragmentListener.onGetCommentsError(t.getMessage());
                Log.e("comment", t.getMessage());
            }
        });
    }
}
