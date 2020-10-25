package com.example.travelguide.ui.home.comments;

import android.util.Log;

import com.example.travelguide.model.request.AddCommentReplyRequest;
import com.example.travelguide.model.request.AddCommentRequest;
import com.example.travelguide.model.request.CommentRequest;
import com.example.travelguide.model.request.LikeCommentRequest;
import com.example.travelguide.model.response.AddCommentReplyResponse;
import com.example.travelguide.model.response.AddCommentResponse;
import com.example.travelguide.model.response.CommentResponse;
import com.example.travelguide.model.response.LikeCommentResponse;
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

    void getComments(String accessToken, CommentRequest commentRequest) {
        apiService.getStoryComments(accessToken, commentRequest).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0)
                        commentFragmentListener.onGetComments(response.body());
                    else
                        commentFragmentListener.onError(response.body().getStatus() + " ");
                } else {
                    commentFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                commentFragmentListener.onError(t.getMessage());
            }
        });
    }

    void addComment(String accessToken, AddCommentRequest addCommentRequest) {
        apiService.addStoryComment(accessToken, addCommentRequest).enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        commentFragmentListener.onAddComment(response.body());
                    } else {
                        commentFragmentListener.onError(response.body().getStatus() + " ");
                    }
                } else {
                    commentFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                commentFragmentListener.onError(t.getMessage());
            }
        });
    }

    void addCommentReply(String accessToken, AddCommentReplyRequest addCommentReplyRequest) {
        apiService.addStoryCommentReply(accessToken, addCommentReplyRequest).enqueue(new Callback<AddCommentReplyResponse>() {
            @Override
            public void onResponse(Call<AddCommentReplyResponse> call, Response<AddCommentReplyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        commentFragmentListener.onAddCommentReply(response.body());
                    } else {
                        commentFragmentListener.onError(response.body().getStatus() + " " + response.body().getMessage());
                    }
                } else {
                    commentFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddCommentReplyResponse> call, Throwable t) {
                commentFragmentListener.onError(t.getMessage());
            }
        });
    }

    void likeComment(String accessToken, LikeCommentRequest likeCommentRequest) {
        apiService.likeStoryComment(accessToken, likeCommentRequest).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus() == 0) {
                        commentFragmentListener.onCommentLiked(response.body());
                    } else {
                        commentFragmentListener.onError(response.body().getStatus() + " ");
                    }
                } else {
                    commentFragmentListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                commentFragmentListener.onError(t.getMessage());
            }
        });
    }
}
