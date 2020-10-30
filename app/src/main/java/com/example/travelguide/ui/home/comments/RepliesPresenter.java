package com.example.travelguide.ui.home.comments;

import com.example.travelguide.model.request.AddCommentReplyRequest;
import com.example.travelguide.model.request.GetMoreCommentRequest;
import com.example.travelguide.model.response.AddCommentReplyResponse;
import com.example.travelguide.model.response.MoreReplyResponse;
import com.example.travelguide.network.ApiService;
import com.example.travelguide.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RepliesPresenter {

    private RepliesListener repliesListener;
    private ApiService apiService;

    RepliesPresenter(RepliesListener repliesListener) {
        this.repliesListener = repliesListener;
        this.apiService = RetrofitManager.getApiService();
    }

    void getReplies(String accessToken, GetMoreCommentRequest getMoreCommentRequest) {
        apiService.getMoreCommentReplies(accessToken, getMoreCommentRequest).enqueue(new Callback<MoreReplyResponse>() {
            @Override
            public void onResponse(Call<MoreReplyResponse> call, Response<MoreReplyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    repliesListener.onGetReplies(response.body().getComment_replies());
                } else {
                    repliesListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MoreReplyResponse> call, Throwable t) {
                repliesListener.onError(t.getMessage());
            }
        });
    }

    void addCommentReply(String accessToken, AddCommentReplyRequest addCommentReplyRequest) {
        apiService.addStoryCommentReply(accessToken, addCommentReplyRequest).enqueue(new Callback<AddCommentReplyResponse>() {
            @Override
            public void onResponse(Call<AddCommentReplyResponse> call, Response<AddCommentReplyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        repliesListener.onAddReply(response.body().getComment_replies());
                    } else {
                        repliesListener.onError(String.valueOf(response.body().getStatus()));
                    }
                } else {
                    repliesListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddCommentReplyResponse> call, Throwable t) {
                repliesListener.onError(t.getMessage());
            }
        });
    }
}
